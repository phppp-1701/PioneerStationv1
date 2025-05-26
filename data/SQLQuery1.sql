CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

-- Create tables in the correct order to satisfy foreign key dependencies

-- 1. NhanVien (no dependencies)
CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(50),
    cccd_HoChieu NVARCHAR(20) UNIQUE,
    soDienThoai NVARCHAR(10),
    ngaySinh DATE,
    chucVu NVARCHAR(20) CHECK (chucVu IN ('banVe', 'quanLy')),
    gioiTinh NVARCHAR(5) CHECK (gioiTinh IN ('nam', 'nu')),
    urlAnh NVARCHAR(255),
    trangThaiNhanVien VARCHAR(20) CHECK (trangThaiNhanVien IN ('hoatDong','voHieuHoa')),
    email NVARCHAR(100)
);

-- 2. TaiKhoan (depends on NhanVien)
CREATE TABLE TaiKhoan (
    tenTaiKhoan NVARCHAR(20) PRIMARY KEY,
    matKhau NVARCHAR(50),
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

-- 3. KhachHang (no dependencies)
CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(50),
    cccd_HoChieu NVARCHAR(20),
    soDienThoai NVARCHAR(10),
    loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('thanThiet', 'vip','vangLai')),
    trangThaiKhachHang VARCHAR(20) CHECK (trangThaiKhachHang IN ('hoatDong','voHieuHoa')),
    email NVARCHAR(100)
);

-- 4. KhuyenMai (no dependencies)
CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100),
    ngayBatDauSuKien DATE,
    ngayKetThucSuKien DATE,
    loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('thanThiet', 'vip','vangLai')),
    phanTramGiamGiaSuKien FLOAT
);

-- 5. Ga (no dependencies)
CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);

-- 6. TuyenTau (depends on Ga)
CREATE TABLE TuyenTau (
    maTuyenTau VARCHAR(20) PRIMARY KEY,
    tenTuyenTau NVARCHAR(100),
    khoangCach FLOAT,
    gaDi VARCHAR(20),
    gaDen VARCHAR(20),
    FOREIGN KEY (gaDi) REFERENCES Ga(maGa),
    FOREIGN KEY (gaDen) REFERENCES Ga(maGa)
);

-- 7. Tau (no dependencies)
CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100),
    trangThaiTau NVARCHAR(50) CHECK (trangThaiTau IN ('hoatDong', 'khongHoatDong')),
    loaiTau NVARCHAR(50) CHECK (loaiTau IN ('tauChatLuong', 'tauThongNhat', 'tauDiaPhuong', 'tauDuLich'))
);

-- 8. ChuyenTau (depends on Tau, TuyenTau)
CREATE TABLE ChuyenTau (
    maChuyenTau VARCHAR(30) PRIMARY KEY,
    ngayKhoiHanh DATE,
    gioKhoiHanh TIME,
    ngayDuKien DATE,
    gioDuKien TIME,
    trangThaiChuyenTau NVARCHAR(50) CHECK (trangThaiChuyenTau IN ('hoatDong','khongHoatDong')),
    maTau VARCHAR(20),
    maTuyenTau VARCHAR(20),
    FOREIGN KEY (maTau) REFERENCES Tau(maTau),
    FOREIGN KEY (maTuyenTau) REFERENCES TuyenTau(maTuyenTau)
);

CREATE TABLE ToaTau (
    maToaTau VARCHAR(20) PRIMARY KEY, -- Mã toa tàu, là khóa chính
    thuTuToa INT, -- Thứ tự toa trong tàu
    maTau VARCHAR(20), -- Mã tàu, khóa ngoại tham chiếu đến bảng Tau
    loaiToa VARCHAR(20) NOT NULL, -- Loại toa (giường nằm, ngồi mềm), không được để trống
    soLuongKhoang INT, -- Số lượng khoang (chỉ áp dụng cho loại giường nằm)
    soLuongTang INT, -- Số lượng tầng (chỉ áp dụng cho loại giường nằm)
    soLuongGiuong INT, -- Số lượng giường (chỉ áp dụng cho loại giường nằm)
    soLuongGhe INT, -- Số lượng ghế (chỉ áp dụng cho loại ngồi mềm)
    FOREIGN KEY (maTau) REFERENCES Tau(maTau), -- Khóa ngoại liên kết với bảng Tau
    CONSTRAINT chk_loaiToa CHECK (loaiToa IN ('giuongNam', 'ngoiMem')), -- Ràng buộc kiểm tra loại toa
    CONSTRAINT chk_toa_attributes CHECK (
        -- Ràng buộc kiểm tra thuộc tính dựa trên loại toa
        -- Nếu là 'giuongNam', các trường số lượng khoang, tầng, giường phải có giá trị và > 0, số lượng ghế phải NULL
        (loaiToa = 'giuongNam' AND soLuongKhoang IS NOT NULL AND soLuongKhoang > 0
         AND soLuongTang IS NOT NULL AND soLuongTang > 0
         AND soLuongGiuong IS NOT NULL AND soLuongGiuong > 0
         AND soLuongGhe IS NULL)
        OR
        -- Nếu là 'ngoiMem', trường số lượng ghế phải có giá trị và > 0, các trường số lượng khoang, tầng, giường phải NULL
        (loaiToa = 'ngoiMem' AND soLuongGhe IS NOT NULL AND soLuongGhe > 0
         AND soLuongKhoang IS NULL AND soLuongTang IS NULL AND soLuongGiuong IS NULL)
    )
);
GO

-- 12. Cho (depends on ToaTau)
CREATE TABLE Cho (
    maCho VARCHAR(20) PRIMARY KEY,
    soThuTuCho INT,
    maToaTau VARCHAR(20),
    FOREIGN KEY (maToaTau) REFERENCES ToaTau(maToaTau)
);

-- 13. ChiTietCho (depends on Cho, ChuyenTau, Tau, ToaTau)
CREATE TABLE ChiTietCho (
    maCho VARCHAR(20),
	maChuyenTau VARCHAR(30),
    trangThaiCho NVARCHAR(50) CHECK (trangThaiCho IN ('daBan','dangDat','conTrong')),
    PRIMARY KEY (maCho, maChuyenTau),
    FOREIGN KEY (maCho) REFERENCES Cho(maCho),
    FOREIGN KEY (maChuyenTau) REFERENCES ChuyenTau(maChuyenTau),
);

-- 14. HoaDon (depends on KhachHang, KhuyenMai, ChiTietCaLam)
CREATE TABLE HoaDon (
    maHoaDon VARCHAR(30) PRIMARY KEY, -- Mã hóa đơn, là khóa chính
    ngayTaoHoaDon DATE, -- Ngày tạo hóa đơn
    gioTaoHoaDon TIME, -- Giờ tạo hóa đơn
    phuongThucThanhToan NVARCHAR(50) CHECK (phuongThucThanhToan IN ('tienMat','chuyenKhoan')), -- Phương thức thanh toán
    phanTramGiamGia FLOAT, -- Phần trăm giảm giá
    thanhTien FLOAT NOT NULL, -- Thành tiền (thêm từ entity Java)
    tienKhachDua FLOAT NOT NULL, -- Tiền khách đưa (thêm NOT NULL vì đây là dữ liệu quan trọng)
    tienTraLai FLOAT NOT NULL, -- Tiền trả lại (thêm từ entity Java)
    maKhachHang VARCHAR(20), -- Mã khách hàng, khóa ngoại
    maKhuyenMai VARCHAR(20), -- Mã khuyến mãi, khóa ngoại
    maNhanVien VARCHAR(20), -- Mã nhân viên, khóa ngoại
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang), -- Khóa ngoại liên kết với bảng KhachHang
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai), -- Khóa ngoại liên kết với bảng KhuyenMai
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien) -- Khóa ngoại liên kết với bảng NhanVien
);
GO

-- 15. Ve (depends on HoaDon, ChiTietCho, Tau, ToaTau)
CREATE TABLE Ve (
    maVe VARCHAR(30) PRIMARY KEY,
    ngayTaoVe DATE,
    trangThaiVe NVARCHAR(50) CHECK (trangThaiVe IN ('hieuLuc','daHuy','daDoi','hetHan')),
    tenKhachHang NVARCHAR(100),
    cccd_HoChieu NVARCHAR(20),
    ngaySinh DATE,
    loaiVe NVARCHAR(50) CHECK (loaiVe IN ('nguoiLon','treEm','sinhVien','nguoiCaoTuoi')),
    maHoaDon VARCHAR(30),
    maCho VARCHAR(20),
    maChuyenTau VARCHAR(30),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maCho, maChuyenTau) REFERENCES ChiTietCho(maCho, maChuyenTau),
);

-- Insert data in the correct order to avoid foreign key conflicts

INSERT INTO NhanVien (maNhanVien, tenNhanVien, cccd_HoChieu, soDienThoai, ngaySinh, chucVu, gioiTinh, urlAnh, trangThaiNhanVien, email) VALUES
('2023NV000001', N'Phạm Trương Hoàng Phương', '123456789012', '0901234567', '2004-01-17', 'quanLy', 'nam', 'image/phuong.jpg', 'hoatDong', 'phuong@gmail.com'),
('2023NV000002', N'Phạm Viết Quân', '987654321098', '0912345678', '1995-08-22', 'quanLy', 'nam', 'image/quan.jpg', 'hoatDong', 'quan@gmail.com'),
('2024NV000001', N'Bùi Tấn Quang Trung', '456789123456', '0923456789', '1988-03-10', 'quanLy', 'nam', 'image/trung.jpg', 'hoatDong', 'trung@gmail.com'),
('2024NV000002', N'Trần Minh Tuấn', '789123456789', '0934567890', '1992-11-30', 'quanLy', 'nam', 'image/tuan.jpg', 'hoatDong', 'tuan@gmail.com');
GO

INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien) VALUES
('phuong123', 'matkhau1', '2023NV000001'),
('quan123', 'matkhau2', '2023NV000002'),
('trung123', 'matkhau3', '2024NV000001'),
('tuan123', 'matkhau4', '2024NV000002');
GO

INSERT INTO KhachHang (
    maKhachHang,
    tenKhachHang,
    cccd_HoChieu,
    soDienThoai,
    loaiKhachHang,
    trangThaiKhachHang,
    email
) VALUES
    -- Khách hàng thân thiết
    ('2025KH000001', N'Lê Thị Hương', '001234567890', '0901234567', 'thanThiet', 'hoatDong', 'huong.le@example.com'),
    ('2025KH000002', N'Phạm Văn Long', '002345678901', '0912345678', 'thanThiet', 'hoatDong', 'long.pham@example.com'),
    ('2025KH000003', N'Nguyễn Thị Thu', '004567890123', '0945678901', 'thanThiet', 'hoatDong', 'thu.nguyen@example.com'),
    ('2025KH000004', N'Trần Đình Khôi', '005678901234', '0967890123', 'thanThiet', 'hoatDong', 'khoi.tran@example.com'),
    ('2025KH000007', N'Hoàng Anh Tuấn', '007890123456', '0987654321', 'thanThiet', 'hoatDong', 'tuan.hoang@example.com'),
    ('2025KH000008', N'Bùi Thị Lan', '008901234567', '0998765432', 'thanThiet', 'hoatDong', 'lan.bui@example.com'),
    -- Khách hàng VIP
    ('2025KH000005', N'Đặng Thị Mai', '003456789012', '0934567890', 'vip', 'hoatDong', 'mai.dang@example.com'),
    ('2025KH000006', N'Võ Minh Quân', '006789012345', '0978901234', 'vip', 'hoatDong', 'quan.vo@example.com'),
    ('2025KH000009', N'Lê Văn Cường', '009012345678', '0956789012', 'vip', 'hoatDong', 'cuong.le@example.com'),
    ('2025KH000010', N'Phạm Thị Yến', '010123456789', '0923456789', 'vip', 'hoatDong', 'yen.pham@example.com'),
    -- Khách hàng vãng lai (chỉ một mã KHVL duy nhất)
    ('KHVL', NULL, NULL, NULL, 'vangLai', 'hoatDong', NULL);
go

-- Chèn dữ liệu mẫu cho bảng KhuyenMai với tên khuyến mãi thực tế hơn

INSERT INTO KhuyenMai (
    maKhuyenMai,
    tenKhuyenMai,
    ngayBatDauSuKien,
    ngayKetThucSuKien,
    loaiKhachHang,
    phanTramGiamGiaSuKien
) VALUES
    -- Khuyến mãi cho khách hàng thân thiết (độc quyền)
    ('2025KM001', N'Ưu đãi Vàng - Giảm 10% cho thành viên thân thiết', '2025-06-01', '2025-06-30', 'thanThiet', 0.10),
    ('2024KM002', N'Thẻ tích điểm - Nhân đôi điểm thưởng mùa hè', '2024-07-15', '2024-08-15', 'thanThiet', 0.05),

    -- Khuyến mãi cho khách hàng VIP (độc quyền)
    ('2025KM003', N'Đặc quyền Kim Cương - Giảm 20% toàn bộ dịch vụ', '2025-05-20', '2025-06-20', 'vip', 0.20),
    ('2025KM004', N'Quà tặng Sinh nhật VIP - Ưu đãi đặc biệt', '2025-04-01', '2025-04-30', 'vip', 0.15),

    -- Chương trình "Ưu đãi Chào mừng" áp dụng cho cả 3 loại khách hàng
    ('2025KM005', N'Ưu đãi Chào mừng - Giảm 5% cho lần đầu', '2025-06-10', '2025-07-10', 'vangLai', 0.05),
    ('2025KM007', N'Ưu đãi Chào mừng - Giảm 5% cho lần đầu', '2025-06-10', '2025-07-10', 'thanThiet', 0.05),
    ('2025KM008', N'Ưu đãi Chào mừng - Giảm 5% cho lần đầu', '2025-06-10', '2025-07-10', 'vip', 0.05),

    -- Chương trình "Khuyến mãi cuối năm" áp dụng cho cả 3 loại khách hàng
    ('2025KM006', N'Khuyến mãi cuối năm - Tri ân khách hàng', '2025-01-01', '2025-12-31', 'vangLai', 0.02),
    ('2025KM009', N'Khuyến mãi cuối năm - Tri ân khách hàng', '2025-01-01', '2025-12-31', 'thanThiet', 0.02),
    ('2025KM010', N'Khuyến mãi cuối năm - Tri ân khách hàng', '2025-01-01', '2025-12-31', 'vip', 0.02);
go

INSERT INTO Ga (
    maGa,
    tenGa,
    diaChi
) VALUES
    ('2025GA0001', N'Ga Sài Gòn', N'Số 1 Nguyễn Thông, Phường 9, Quận 3, TP. Hồ Chí Minh'),
    ('2025GA0002', N'Ga Hà Nội', N'120 Đường Lê Duẩn, Phường Cửa Nam, Quận Hoàn Kiếm, Hà Nội'),
    ('2025GA0003', N'Ga Đà Nẵng', N'791 Đường Hải Phòng, Phường Chính Gián, Quận Thanh Khê, Đà Nẵng'),
    ('2025GA0004', N'Ga Nha Trang', N'17 Thái Nguyên, Phường Phước Tân, TP. Nha Trang, Khánh Hòa'),
    ('2025GA0005', N'Ga Huế', N'2 Bùi Thị Xuân, Phường Đúc, TP. Huế, Thừa Thiên Huế'),
    ('2024GA0006', N'Ga Vinh', N'1 Lý Thường Kiệt, Phường Lê Lợi, TP. Vinh, Nghệ An'),
    ('2024GA0007', N'Ga Đồng Hới', N'Tiểu khu 4, Phường Nam Lý, TP. Đồng Hới, Quảng Bình'),
    ('2023GA0008', N'Ga Phan Thiết', N'1 Lê Duẩn, Phường Phú Trinh, TP. Phan Thiết, Bình Thuận'),
    ('2023GA0009', N'Ga Biên Hòa', N'297 Hưng Đạo Vương, Phường Trung Dũng, TP. Biên Hòa, Đồng Nai'),
    ('2025GA0010', N'Ga Hải Phòng', N'75 Lương Khánh Thiện, Phường Ngô Quyền, Quận Ngô Quyền, Hải Phòng');
go

INSERT INTO TuyenTau (
    maTuyenTau,
    tenTuyenTau,
    khoangCach,
    gaDi,
    gaDen
) VALUES
    -- Tuyến Sài Gòn - Hà Nội và ngược lại
    ('2025TT0001', N'Tuyến Sài Gòn - Hà Nội', 1726.0, '2025GA0001', '2025GA0002'),
    ('2025TT0002', N'Tuyến Hà Nội - Sài Gòn', 1726.0, '2025GA0002', '2025GA0001'),

    -- Tuyến Sài Gòn - Đà Nẵng và ngược lại
    ('2025TT0003', N'Tuyến Sài Gòn - Đà Nẵng', 961.0, '2025GA0001', '2025GA0003'),
    ('2025TT0004', N'Tuyến Đà Nẵng - Sài Gòn', 961.0, '2025GA0003', '2025GA0001'),

    -- Tuyến Sài Gòn - Nha Trang và ngược lại
    ('2025TT0005', N'Tuyến Sài Gòn - Nha Trang', 411.0, '2025GA0001', '2025GA0004'),
    ('2025TT0006', N'Tuyến Nha Trang - Sài Gòn', 411.0, '2025GA0004', '2025GA0001'),

    -- Tuyến Hà Nội - Đà Nẵng và ngược lại
    ('2025TT0007', N'Tuyến Hà Nội - Đà Nẵng', 791.0, '2025GA0002', '2025GA0003'),
    ('2025TT0008', N'Tuyến Đà Nẵng - Hà Nội', 791.0, '2025GA0003', '2025GA0002'),

    -- Tuyến Đà Nẵng - Huế và ngược lại
    ('2025TT0009', N'Tuyến Đà Nẵng - Huế', 103.0, '2025GA0003', '2025GA0005'),
    ('2025TT0010', N'Tuyến Huế - Đà Nẵng', 103.0, '2025GA0005', '2025GA0003'),

    -- Tuyến Hà Nội - Vinh và ngược lại
    ('2025TT0011', N'Tuyến Hà Nội - Vinh', 319.0, '2025GA0002', '2024GA0006'),
    ('2025TT0012', N'Tuyến Vinh - Hà Nội', 319.0, '2024GA0006', '2025GA0002');
go

INSERT INTO Tau (
    maTau,
    tenTau,
    trangThaiTau,
    loaiTau
) VALUES
    -- Tàu chất lượng
    ('2025SE010001', N'SE01', N'hoatDong', N'tauChatLuong'),
    ('2025SE020002', N'SE02', N'hoatDong', N'tauChatLuong'),

    -- Tàu thống nhất (từ SE03 đến SE20)
    ('2025SE030003', N'SE03', N'hoatDong', N'tauThongNhat'),
    ('2025SE040004', N'SE04', N'hoatDong', N'tauThongNhat'),
    ('2025SE050005', N'SE05', N'hoatDong', N'tauThongNhat'),
    ('2025SE060006', N'SE06', N'hoatDong', N'tauThongNhat'),
    ('2025SE070007', N'SE07', N'hoatDong', N'tauThongNhat'),
    ('2025SE080018', N'SE08', N'hoatDong', N'tauThongNhat'),
    ('2025SE090019', N'SE09', N'hoatDong', N'tauThongNhat'),
    ('2025SE100020', N'SE10', N'hoatDong', N'tauThongNhat'),
    ('2025SE110021', N'SE11', N'hoatDong', N'tauThongNhat'),
    ('2025SE120022', N'SE12', N'hoatDong', N'tauThongNhat'),
    ('2025SE130023', N'SE13', N'hoatDong', N'tauThongNhat'),
    ('2025SE140024', N'SE14', N'hoatDong', N'tauThongNhat'),
    ('2025SE150025', N'SE15', N'hoatDong', N'tauThongNhat'),
    ('2025SE160026', N'SE16', N'hoatDong', N'tauThongNhat'),
    ('2025SE170027', N'SE17', 'hoatDong', N'tauThongNhat'),
    ('2025SE180028', N'SE18', N'hoatDong', N'tauThongNhat'),
    ('2025SE190029', N'SE19', N'hoatDong', N'tauThongNhat'),
    ('2025SE200030', N'SE20', N'hoatDong', N'tauThongNhat'),

    -- Tàu địa phương (từ DP01 đến DP10)
    ('2025DP010008', N'DP01', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP020009', N'DP02', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP030010', N'DP03', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP040011', N'DP04', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP050012', N'DP05', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP060031', N'DP06', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP070032', N'DP07', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP080033', N'DP08', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP090034', N'DP09', N'hoatDong', N'tauDiaPhuong'),
    ('2025DP100035', N'DP10', N'hoatDong', N'tauDiaPhuong'),

    -- Tàu du lịch (từ DL01 đến DL10)
    ('2025DL010013', N'DL01', N'hoatDong', N'tauDuLich'),
    ('2025DL020014', N'DL02', N'hoatDong', N'tauDuLich'),
    ('2025DL030015', N'DL03', N'hoatDong', N'tauDuLich'),
    ('2025DL040016', N'DL04', N'hoatDong', N'tauDuLich'),
    ('2025DL050017', N'DL05', N'hoatDong', N'tauDuLich'),
    ('2025DL060036', N'DL06', N'hoatDong', N'tauDuLich'),
    ('2025DL070037', N'DL07', N'hoatDong', N'tauDuLich'),
    ('2025DL080038', N'DL08', N'hoatDong', N'tauDuLich'),
    ('2025DL090039', N'DL09', N'hoatDong', N'tauDuLich'),
    ('2025DL100040', N'DL10', N'hoatDong', N'tauDuLich');
GO

-- Chèn dữ liệu mẫu cho bảng ToaTau dựa trên các tàu đã có
-- Mỗi tàu sẽ có từ 6 đến 8 toa ngẫu nhiên (giường nằm hoặc ngồi mềm)

INSERT INTO ToaTau (
    maToaTau,
    thuTuToa,
    maTau,
    loaiToa,
    soLuongKhoang,
    soLuongTang,
    soLuongGiuong,
    soLuongGhe
) VALUES
    -- Dữ liệu cho tàu SE01 (maTau: 2025SE010001)
    ('2025SE01TT01001', 1, '2025SE010001', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE01TT02002', 2, '2025SE010001', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE01TT03003', 3, '2025SE010001', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE01TT04004', 4, '2025SE010001', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE01TT05005', 5, '2025SE010001', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE01TT06006', 6, '2025SE010001', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE01TT07007', 7, '2025SE010001', N'giuongNam', 6, 2, 24, NULL),

    -- Dữ liệu cho tàu SE02 (maTau: 2025SE020002)
    ('2025SE02TT01008', 1, '2025SE020002', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE02TT02009', 2, '2025SE020002', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE02TT03010', 3, '2025SE020002', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE02TT04011', 4, '2025SE020002', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE02TT05012', 5, '2025SE020002', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE02TT06013', 6, '2025SE020002', N'giuongNam', 6, 3, 36, NULL),
    ('2025SE02TT07014', 7, '2025SE020002', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE02TT08015', 8, '2025SE020002', N'giuongNam', 5, 2, 20, NULL),

    -- Dữ liệu cho tàu SE03 (maTau: 2025SE030003)
    ('2025SE03TT01016', 1, '2025SE030003', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE03TT02017', 2, '2025SE030003', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE03TT03018', 3, '2025SE030003', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE03TT04019', 4, '2025SE030003', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE03TT05020', 5, '2025SE030003', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE03TT06021', 6, '2025SE030003', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu SE04 (maTau: 2025SE040004)
    ('2025SE04TT01022', 1, '2025SE040004', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE04TT02023', 2, '2025SE040004', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE04TT03024', 3, '2025SE040004', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE04TT04025', 4, '2025SE040004', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE04TT05026', 5, '2025SE040004', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE04TT06027', 6, '2025SE040004', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE04TT07028', 7, '2025SE040004', N'ngoiMem', NULL, NULL, NULL, 72),

    -- Dữ liệu cho tàu SE05 (maTau: 2025SE050005)
    ('2025SE05TT01029', 1, '2025SE050005', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE05TT02030', 2, '2025SE050005', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE05TT03031', 3, '2025SE050005', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE05TT04032', 4, '2025SE050005', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE05TT05033', 5, '2025SE050005', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE05TT06034', 6, '2025SE050005', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE05TT07035', 7, '2025SE050005', N'giuongNam', 5, 3, 30, NULL),
    ('2025SE05TT08036', 8, '2025SE050005', N'ngoiMem', NULL, NULL, NULL, 56),

    -- Dữ liệu cho tàu SE06 (maTau: 2025SE060006)
    ('2025SE06TT01037', 1, '2025SE060006', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE06TT02038', 2, '2025SE060006', N'giuongNam', 9, 2, 36, NULL),
    ('2025SE06TT03039', 3, '2025SE060006', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE06TT04040', 4, '2025SE060006', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE06TT05041', 5, '2025SE060006', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE06TT06042', 6, '2025SE060006', N'giuongNam', 8, 2, 32, NULL),

    -- Dữ liệu cho tàu SE07 (maTau: 2025SE070007)
    ('2025SE07TT01043', 1, '2025SE070007', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE07TT02044', 2, '2025SE070007', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE07TT03045', 3, '2025SE070007', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE07TT04046', 4, '2025SE070007', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE07TT05047', 5, '2025SE070007', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE07TT06048', 6, '2025SE070007', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE07TT07049', 7, '2025SE070007', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE07TT08050', 8, '2025SE070007', N'ngoiMem', NULL, NULL, NULL, 64),

    -- Dữ liệu cho tàu SE08 (maTau: 2025SE080018)
    ('2025SE08TT01051', 1, '2025SE080018', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE08TT02052', 2, '2025SE080018', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE08TT03053', 3, '2025SE080018', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE08TT04054', 4, '2025SE080018', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE08TT05055', 5, '2025SE080018', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE08TT06056', 6, '2025SE080018', N'giuongNam', 6, 3, 36, NULL),

    -- Dữ liệu cho tàu SE09 (maTau: 2025SE090019)
    ('2025SE09TT01057', 1, '2025SE090019', N'giuongNam', 9, 2, 36, NULL),
    ('2025SE09TT02058', 2, '2025SE090019', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE09TT03059', 3, '2025SE090019', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE09TT04060', 4, '2025SE090019', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE09TT05061', 5, '2025SE090019', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE09TT06062', 6, '2025SE090019', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE09TT07063', 7, '2025SE090019', N'giuongNam', 5, 3, 30, NULL),

    -- Dữ liệu cho tàu SE10 (maTau: 2025SE100020)
    ('2025SE10TT01064', 1, '2025SE100020', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE10TT02065', 2, '2025SE100020', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE10TT03066', 3, '2025SE100020', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE10TT04067', 4, '2025SE100020', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE10TT05068', 5, '2025SE100020', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE10TT06069', 6, '2025SE100020', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE10TT07070', 7, '2025SE100020', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE10TT08071', 8, '2025SE100020', N'giuongNam', 9, 3, 54, NULL),

    -- Dữ liệu cho tàu SE11 (maTau: 2025SE110021)
    ('2025SE11TT01072', 1, '2025SE110021', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE11TT02073', 2, '2025SE110021', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE11TT03074', 3, '2025SE110021', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE11TT04075', 4, '2025SE110021', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE11TT05076', 5, '2025SE110021', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE11TT06077', 6, '2025SE110021', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu SE12 (maTau: 2025SE120022)
    ('2025SE12TT01078', 1, '2025SE120022', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE12TT02079', 2, '2025SE120022', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE12TT03080', 3, '2025SE120022', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE12TT04081', 4, '2025SE120022', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE12TT05082', 5, '2025SE120022', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE12TT06083', 6, '2025SE120022', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE12TT07084', 7, '2025SE120022', N'ngoiMem', NULL, NULL, NULL, 72),

    -- Dữ liệu cho tàu SE13 (maTau: 2025SE130023)
    ('2025SE13TT01085', 1, '2025SE130023', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE13TT02086', 2, '2025SE130023', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE13TT03087', 3, '2025SE130023', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE13TT04088', 4, '2025SE130023', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE13TT05089', 5, '2025SE130023', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE13TT06090', 6, '2025SE130023', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE13TT07091', 7, '2025SE130023', N'giuongNam', 5, 3, 30, NULL),
    ('2025SE13TT08092', 8, '2025SE130023', N'ngoiMem', NULL, NULL, NULL, 56),

    -- Dữ liệu cho tàu SE14 (maTau: 2025SE140024)
    ('2025SE14TT01093', 1, '2025SE140024', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE14TT02094', 2, '2025SE140024', N'giuongNam', 9, 2, 36, NULL),
    ('2025SE14TT03095', 3, '2025SE140024', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE14TT04096', 4, '2025SE140024', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE14TT05097', 5, '2025SE140024', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE14TT06098', 6, '2025SE140024', N'giuongNam', 8, 2, 32, NULL),

    -- Dữ liệu cho tàu SE15 (maTau: 2025SE150025)
    ('2025SE15TT01099', 1, '2025SE150025', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE15TT02100', 2, '2025SE150025', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE15TT03101', 3, '2025SE150025', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE15TT04102', 4, '2025SE150025', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE15TT05103', 5, '2025SE150025', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE15TT06104', 6, '2025SE150025', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE15TT07105', 7, '2025SE150025', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE15TT08106', 8, '2025SE150025', N'ngoiMem', NULL, NULL, NULL, 64),

    -- Dữ liệu cho tàu SE16 (maTau: 2025SE160026)
    ('2025SE16TT01107', 1, '2025SE160026', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE16TT02108', 2, '2025SE160026', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE16TT03109', 3, '2025SE160026', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE16TT04110', 4, '2025SE160026', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE16TT05111', 5, '2025SE160026', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE16TT06112', 6, '2025SE160026', N'giuongNam', 6, 3, 36, NULL),

    -- Dữ liệu cho tàu SE17 (maTau: 2025SE170027)
    ('2025SE17TT01113', 1, '2025SE170027', N'giuongNam', 9, 2, 36, NULL),
    ('2025SE17TT02114', 2, '2025SE170027', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE17TT03115', 3, '2025SE170027', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE17TT04116', 4, '2025SE170027', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE17TT05117', 5, '2025SE170027', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE17TT06118', 6, '2025SE170027', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE17TT07119', 7, '2025SE170027', N'giuongNam', 5, 3, 30, NULL),

    -- Dữ liệu cho tàu SE18 (maTau: 2025SE180028)
    ('2025SE18TT01120', 1, '2025SE180028', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE18TT02121', 2, '2025SE180028', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE18TT03122', 3, '2025SE180028', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE18TT04123', 4, '2025SE180028', N'giuongNam', 7, 3, 42, NULL),
    ('2025SE18TT05124', 5, '2025SE180028', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025SE18TT06125', 6, '2025SE180028', N'giuongNam', 8, 2, 32, NULL),
    ('2025SE18TT07126', 7, '2025SE180028', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE18TT08127', 8, '2025SE180028', N'giuongNam', 9, 3, 54, NULL),

    -- Dữ liệu cho tàu SE19 (maTau: 2025SE190029)
    ('2025SE19TT01128', 1, '2025SE190029', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE19TT02129', 2, '2025SE190029', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE19TT03130', 3, '2025SE190029', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE19TT04131', 4, '2025SE190029', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025SE19TT05132', 5, '2025SE190029', N'giuongNam', 6, 2, 24, NULL),
    ('2025SE19TT06133', 6, '2025SE190029', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu SE20 (maTau: 2025SE200030)
    ('2025SE20TT01134', 1, '2025SE200030', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025SE20TT02135', 2, '2025SE200030', N'giuongNam', 9, 3, 54, NULL),
    ('2025SE20TT03136', 3, '2025SE200030', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025SE20TT04137', 4, '2025SE200030', N'giuongNam', 7, 2, 28, NULL),
    ('2025SE20TT05138', 5, '2025SE200030', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025SE20TT06139', 6, '2025SE200030', N'giuongNam', 8, 3, 48, NULL),
    ('2025SE20TT07140', 7, '2025SE200030', N'ngoiMem', NULL, NULL, NULL, 72),

    -- Dữ liệu cho tàu DP01 (maTau: 2025DP010008)
    ('2025DP01TT01141', 1, '2025DP010008', N'giuongNam', 6, 2, 24, NULL),
    ('2025DP01TT02142', 2, '2025DP010008', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP01TT03143', 3, '2025DP010008', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP01TT04144', 4, '2025DP010008', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP01TT05145', 5, '2025DP010008', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP01TT06146', 6, '2025DP010008', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DP01TT07147', 7, '2025DP010008', N'giuongNam', 5, 3, 30, NULL),

    -- Dữ liệu cho tàu DP02 (maTau: 2025DP020009)
    ('2025DP02TT01148', 1, '2025DP020009', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DP02TT02149', 2, '2025DP020009', N'giuongNam', 9, 2, 36, NULL),
    ('2025DP02TT03150', 3, '2025DP020009', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP02TT04151', 4, '2025DP020009', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP02TT05152', 5, '2025DP020009', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP02TT06153', 6, '2025DP020009', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP02TT07154', 7, '2025DP020009', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP02TT08155', 8, '2025DP020009', N'giuongNam', 6, 3, 36, NULL),

    -- Dữ liệu cho tàu DP03 (maTau: 2025DP030010)
    ('2025DP03TT01156', 1, '2025DP030010', N'giuongNam', 8, 3, 48, NULL),
    ('2025DP03TT02157', 2, '2025DP030010', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DP03TT03158', 3, '2025DP030010', N'giuongNam', 7, 2, 28, NULL),
    ('2025DP03TT04159', 4, '2025DP030010', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP03TT05160', 5, '2025DP030010', N'giuongNam', 9, 3, 54, NULL),
    ('2025DP03TT06161', 6, '2025DP030010', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu DP04 (maTau: 2025DP040011)
    ('2025DP04TT01162', 1, '2025DP040011', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP04TT02163', 2, '2025DP040011', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP04TT03164', 3, '2025DP040011', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DP04TT04165', 4, '2025DP040011', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP04TT05166', 5, '2025DP040011', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP04TT06167', 6, '2025DP040011', N'giuongNam', 6, 3, 36, NULL),
    ('2025DP04TT07168', 7, '2025DP040011', N'ngoiMem', NULL, NULL, NULL, 64),

    -- Dữ liệu cho tàu DP05 (maTau: 2025DP050012)
    ('2025DP05TT01169', 1, '2025DP050012', N'giuongNam', 9, 2, 36, NULL),
    ('2025DP05TT02170', 2, '2025DP050012', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP05TT03171', 3, '2025DP050012', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP05TT04172', 4, '2025DP050012', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP05TT05173', 5, '2025DP050012', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP05TT06174', 6, '2025DP050012', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DP05TT07175', 7, '2025DP050012', N'giuongNam', 5, 3, 30, NULL),
    ('2025DP05TT08176', 8, '2025DP050012', N'ngoiMem', NULL, NULL, NULL, 56),

    -- Dữ liệu cho tàu DP06 (maTau: 2025DP060031)
    ('2025DP06TT01177', 1, '2025DP060031', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP06TT02178', 2, '2025DP060031', N'giuongNam', 9, 2, 36, NULL),
    ('2025DP06TT03179', 3, '2025DP060031', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP06TT04180', 4, '2025DP060031', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP06TT05181', 5, '2025DP060031', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP06TT06182', 6, '2025DP060031', N'giuongNam', 8, 2, 32, NULL),

    -- Dữ liệu cho tàu DP07 (maTau: 2025DP070032)
    ('2025DP07TT01183', 1, '2025DP070032', N'giuongNam', 8, 3, 48, NULL),
    ('2025DP07TT02184', 2, '2025DP070032', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DP07TT03185', 3, '2025DP070032', N'giuongNam', 7, 2, 28, NULL),
    ('2025DP07TT04186', 4, '2025DP070032', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP07TT05187', 5, '2025DP070032', N'giuongNam', 9, 3, 54, NULL),
    ('2025DP07TT06188', 6, '2025DP070032', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP07TT07189', 7, '2025DP070032', N'giuongNam', 6, 2, 24, NULL),
    ('2025DP07TT08190', 8, '2025DP070032', N'ngoiMem', NULL, NULL, NULL, 64),

    -- Dữ liệu cho tàu DP08 (maTau: 2025DP080033)
    ('2025DP08TT01191', 1, '2025DP080033', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DP08TT02192', 2, '2025DP080033', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP08TT03193', 3, '2025DP080033', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DP08TT04194', 4, '2025DP080033', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP08TT05195', 5, '2025DP080033', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP08TT06196', 6, '2025DP080033', N'giuongNam', 6, 3, 36, NULL),

    -- Dữ liệu cho tàu DP09 (maTau: 2025DP090034)
    ('2025DP09TT01197', 1, '2025DP090034', N'giuongNam', 9, 2, 36, NULL),
    ('2025DP09TT02198', 2, '2025DP090034', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP09TT03199', 3, '2025DP090034', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP09TT04200', 4, '2025DP090034', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP09TT05201', 5, '2025DP090034', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP09TT06202', 6, '2025DP090034', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DP09TT07203', 7, '2025DP090034', N'giuongNam', 5, 3, 30, NULL),

    -- Dữ liệu cho tàu DP10 (maTau: 2025DP100035)
    ('2025DP10TT01204', 1, '2025DP100035', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DP10TT02205', 2, '2025DP100035', N'giuongNam', 6, 2, 24, NULL),
    ('2025DP10TT03206', 3, '2025DP100035', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DP10TT04207', 4, '2025DP100035', N'giuongNam', 7, 3, 42, NULL),
    ('2025DP10TT05208', 5, '2025DP100035', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DP10TT06209', 6, '2025DP100035', N'giuongNam', 8, 2, 32, NULL),
    ('2025DP10TT07210', 7, '2025DP100035', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DP10TT08211', 8, '2025DP100035', N'giuongNam', 9, 3, 54, NULL),

    -- Dữ liệu cho tàu DL01 (maTau: 2025DL010013)
    ('2025DL01TT01212', 1, '2025DL010013', N'giuongNam', 7, 2, 28, NULL),
    ('2025DL01TT02213', 2, '2025DL010013', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL01TT03214', 3, '2025DL010013', N'giuongNam', 8, 3, 48, NULL),
    ('2025DL01TT04215', 4, '2025DL010013', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL01TT05216', 5, '2025DL010013', N'giuongNam', 6, 2, 24, NULL),
    ('2025DL01TT06217', 6, '2025DL010013', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu DL02 (maTau: 2025DL020014)
    ('2025DL02TT01218', 1, '2025DL020014', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL02TT02219', 2, '2025DL020014', N'giuongNam', 9, 3, 54, NULL),
    ('2025DL02TT03220', 3, '2025DL020014', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DL02TT04221', 4, '2025DL020014', N'giuongNam', 7, 2, 28, NULL),
    ('2025DL02TT05222', 5, '2025DL020014', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL02TT06223', 6, '2025DL020014', N'giuongNam', 8, 3, 48, NULL),
    ('2025DL02TT07224', 7, '2025DL020014', N'ngoiMem', NULL, NULL, NULL, 72),

    -- Dữ liệu cho tàu DL03 (maTau: 2025DL030015)
    ('2025DL03TT01225', 1, '2025DL030015', N'giuongNam', 6, 2, 24, NULL),
    ('2025DL03TT02226', 2, '2025DL030015', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DL03TT03227', 3, '2025DL030015', N'giuongNam', 7, 3, 42, NULL),
    ('2025DL03TT04228', 4, '2025DL030015', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL03TT05229', 5, '2025DL030015', N'giuongNam', 8, 2, 32, NULL),
    ('2025DL03TT06230', 6, '2025DL030015', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DL03TT07231', 7, '2025DL030015', N'giuongNam', 5, 3, 30, NULL),
    ('2025DL03TT08232', 8, '2025DL030015', N'ngoiMem', NULL, NULL, NULL, 56),

    -- Dữ liệu cho tàu DL04 (maTau: 2025DL040016)
    ('2025DL04TT01233', 1, '2025DL040016', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL04TT02234', 2, '2025DL040016', N'giuongNam', 9, 2, 36, NULL),
    ('2025DL04TT03235', 3, '2025DL040016', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DL04TT04236', 4, '2025DL040016', N'giuongNam', 7, 3, 42, NULL),
    ('2025DL04TT05237', 5, '2025DL040016', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL04TT06238', 6, '2025DL040016', N'giuongNam', 8, 2, 32, NULL),

    -- Dữ liệu cho tàu DL05 (maTau: 2025DL050017)
    ('2025DL05TT01239', 1, '2025DL050017', N'giuongNam', 8, 3, 48, NULL),
    ('2025DL05TT02240', 2, '2025DL050017', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL05TT03241', 3, '2025DL050017', N'giuongNam', 7, 2, 28, NULL),
    ('2025DL05TT04242', 4, '2025DL050017', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL05TT05243', 5, '2025DL050017', N'giuongNam', 9, 3, 54, NULL),
    ('2025DL05TT06244', 6, '2025DL050017', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DL05TT07245', 7, '2025DL050017', N'giuongNam', 6, 2, 24, NULL),
    ('2025DL05TT08246', 8, '2025DL050017', N'ngoiMem', NULL, NULL, NULL, 64),

    -- Dữ liệu cho tàu DL06 (maTau: 2025DL060036)
    ('2025DL06TT01247', 1, '2025DL060036', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DL06TT02248', 2, '2025DL060036', N'giuongNam', 7, 3, 42, NULL),
    ('2025DL06TT03249', 3, '2025DL060036', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL06TT04250', 4, '2025DL060036', N'giuongNam', 8, 2, 32, NULL),
    ('2025DL06TT05251', 5, '2025DL060036', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL06TT06252', 6, '2025DL060036', N'giuongNam', 6, 3, 36, NULL),

    -- Dữ liệu cho tàu DL07 (maTau: 2025DL070037)
    ('2025DL07TT01253', 1, '2025DL070037', N'giuongNam', 9, 2, 36, NULL),
    ('2025DL07TT02254', 2, '2025DL070037', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DL07TT03255', 3, '2025DL070037', N'giuongNam', 7, 3, 42, NULL),
    ('2025DL07TT04256', 4, '2025DL070037', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL07TT05257', 5, '2025DL070037', N'giuongNam', 8, 2, 32, NULL),
    ('2025DL07TT06258', 6, '2025DL070037', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DL07TT07259', 7, '2025DL070037', N'giuongNam', 5, 3, 30, NULL),

    -- Dữ liệu cho tàu DL08 (maTau: 2025DL080038)
    ('2025DL08TT01260', 1, '2025DL080038', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL08TT02261', 2, '2025DL080038', N'giuongNam', 6, 2, 24, NULL),
    ('2025DL08TT03262', 3, '2025DL080038', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL08TT04263', 4, '2025DL080038', N'giuongNam', 7, 3, 42, NULL),
    ('2025DL08TT05264', 5, '2025DL080038', N'ngoiMem', NULL, NULL, NULL, 48),
    ('2025DL08TT06265', 6, '2025DL080038', N'giuongNam', 8, 2, 32, NULL),
    ('2025DL08TT07266', 7, '2025DL080038', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL08TT08267', 8, '2025DL080038', N'giuongNam', 9, 3, 54, NULL),

    -- Dữ liệu cho tàu DL09 (maTau: 2025DL090039)
    ('2025DL09TT01268', 1, '2025DL090039', N'giuongNam', 7, 2, 28, NULL),
    ('2025DL09TT02269', 2, '2025DL090039', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL09TT03270', 3, '2025DL090039', N'giuongNam', 8, 3, 48, NULL),
    ('2025DL09TT04271', 4, '2025DL090039', N'ngoiMem', NULL, NULL, NULL, 72),
    ('2025DL09TT05272', 5, '2025DL090039', N'giuongNam', 6, 2, 24, NULL),
    ('2025DL09TT06273', 6, '2025DL090039', N'ngoiMem', NULL, NULL, NULL, 48),

    -- Dữ liệu cho tàu DL10 (maTau: 2025DL100040)
    ('2025DL10TT01274', 1, '2025DL100040', N'ngoiMem', NULL, NULL, NULL, 64),
    ('2025DL10TT02275', 2, '2025DL100040', N'giuongNam', 9, 3, 54, NULL),
    ('2025DL10TT03276', 3, '2025DL100040', N'ngoiMem', NULL, NULL, NULL, 80),
    ('2025DL10TT04277', 4, '2025DL100040', N'giuongNam', 7, 2, 28, NULL),
    ('2025DL10TT05278', 5, '2025DL100040', N'ngoiMem', NULL, NULL, NULL, 56),
    ('2025DL10TT06279', 6, '2025DL100040', N'giuongNam', 8, 3, 48, NULL),
    ('2025DL10TT07280', 7, '2025DL100040', N'ngoiMem', NULL, NULL, NULL, 72);
GO

-- SQL Script để tạo dữ liệu cho bảng Cho dựa trên dữ liệu ToaTau

-- Bước 1: (Tùy chọn) Xóa dữ liệu hiện có trong bảng Cho để tránh trùng lặp khi chạy lại script
-- Cẩn thận khi sử dụng lệnh DELETE trong môi trường production!
DELETE FROM Cho;
GO

-- Bước 2: Tạo dữ liệu Cho cho từng toa tàu
DECLARE @maToaTau VARCHAR(20);
DECLARE @loaiToa NVARCHAR(20);
DECLARE @soLuongCho INT; -- Tổng số giường hoặc ghế trong toa
DECLARE @currentCho INT; -- Biến đếm cho số thứ tự chỗ
DECLARE @maCho VARCHAR(40); -- Mã chỗ

-- Tạo một CURSOR để duyệt qua từng toa tàu
DECLARE toa_tau_cursor CURSOR FOR
SELECT maToaTau, loaiToa,
       CASE
           WHEN loaiToa = 'giuongNam' THEN soLuongGiuong
           WHEN loaiToa = 'ngoiMem' THEN soLuongGhe
           ELSE 0 -- Trường hợp không xác định hoặc lỗi
       END AS TongSoCho
FROM ToaTau;

OPEN toa_tau_cursor;

FETCH NEXT FROM toa_tau_cursor INTO @maToaTau, @loaiToa, @soLuongCho;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @currentCho = 1;
    WHILE @currentCho <= @soLuongCho
    BEGIN
        -- Định dạng số thứ tự chỗ thành 2 chữ số (ví dụ: 01, 02, ...)
        SET @maCho = @maToaTau + FORMAT(@currentCho, 'D2');

        -- Chèn dữ liệu vào bảng Cho
        INSERT INTO Cho (maCho, soThuTuCho, maToaTau)
        VALUES (@maCho, @currentCho, @maToaTau);

        SET @currentCho = @currentCho + 1;
    END;

    FETCH NEXT FROM toa_tau_cursor INTO @maToaTau, @loaiToa, @soLuongCho;
END;

CLOSE toa_tau_cursor;
DEALLOCATE toa_tau_cursor;

PRINT N'Đã tạo dữ liệu chỗ thành công cho tất cả các toa tàu.';
GO

-- Script tạo dữ liệu cho bảng ChuyenTau với định dạng mã chuyến tàu: ddMMyyyy + AAAA (tên tàu) + TT + maTuyenTau + AAA
-- Yêu cầu: Mỗi tuyến có từ 2 đến 6 chuyến tàu, mỗi ngày chỉ có 1 tàu chạy 1 tuyến
-- Thời gian: Từ ngày mai (27/05/2025) đến tuần sau (02/06/2025)
-- Xóa dữ liệu hiện có trong bảng ChuyenTau (cẩn thận khi sử dụng trong môi trường production)
-- Xóa dữ liệu hiện có trong bảng ChuyenTau (cẩn thận khi sử dụng trong môi trường production)
-- Script tạo dữ liệu cho bảng ChuyenTau với định dạng mã chuyến tàu: ddMMyyyy + AAAA (tên tàu) + TT + maTuyenTau + AAA
-- Yêu cầu: Mỗi tàu chỉ chạy 1 chuyến/tuyến, có thể chạy nhiều tuyến khác nhau trong ngày
-- Mỗi ngày, mỗi tuyến có 2-6 chuyến tàu
-- Thời gian: Từ 27/05/2025 đến 02/06/2025 (7 ngày)

USE pioneer_station;
GO

-- Xóa dữ liệu hiện có trong bảng ChuyenTau (cẩn thận khi sử dụng trong môi trường production)
DELETE FROM ChuyenTau;
GO

-- Khai báo biến
DECLARE @maTuyenTau VARCHAR(20);
DECLARE @khoangCach FLOAT;
DECLARE @maTau VARCHAR(20);
DECLARE @tenTau NVARCHAR(100);
DECLARE @soChuyen INT;
DECLARE @currentDate DATE;
DECLARE @gioKhoiHanh TIME;
DECLARE @ngayDuKien DATE;
DECLARE @gioDuKien TIME;
DECLARE @maChuyenTau VARCHAR(30);
DECLARE @chuyenCounter INT;
DECLARE @randomHour INT;
DECLARE @tripIndex INT;

-- Tạo bảng tạm để lưu danh sách tàu phù hợp
DECLARE @TauChon TABLE (
    maTau VARCHAR(20),
    tenTau NVARCHAR(100),
    loaiTau NVARCHAR(50)
);

-- Tạo bảng tạm để theo dõi số thứ tự cho mỗi tuyến
DECLARE @TuyenCounter TABLE (
    maTuyenTau VARCHAR(20),
    counter INT
);

-- Tạo bảng tạm để theo dõi tàu đã sử dụng cho mỗi tuyến
DECLARE @TauTuyenDaDung TABLE (
    maTau VARCHAR(20),
    maTuyenTau VARCHAR(20)
);

-- Khởi tạo counter cho mỗi tuyến
INSERT INTO @TuyenCounter (maTuyenTau, counter)
SELECT maTuyenTau, 1
FROM TuyenTau;

-- Duyệt qua từng ngày từ 27/05/2025 đến 02/06/2025
SET @currentDate = '2025-05-27';
WHILE @currentDate <= '2025-06-02'
BEGIN
    -- Duyệt qua từng tuyến tàu
    DECLARE tuyen_tau_cursor CURSOR FOR
    SELECT maTuyenTau, khoangCach
    FROM TuyenTau;

    OPEN tuyen_tau_cursor;
    FETCH NEXT FROM tuyen_tau_cursor INTO @maTuyenTau, @khoangCach;

    WHILE @@FETCH_STATUS = 0
    BEGIN
        -- Xóa bảng tạm TauChon
        DELETE FROM @TauChon;

        -- Chọn ngẫu nhiên số chuyến từ 2 đến 6 cho tuyến trong ngày
        SET @soChuyen = FLOOR(RAND() * (6 - 2 + 1)) + 2;

        -- Gán tàu phù hợp dựa trên khoảng cách
        IF @khoangCach <= 200 -- Tuyến ngắn: tàu du lịch, địa phương
        BEGIN
            INSERT INTO @TauChon
            SELECT maTau, tenTau, loaiTau
            FROM Tau
            WHERE loaiTau IN ('tauDuLich', 'tauDiaPhuong') AND trangThaiTau = 'hoatDong'
            AND maTau NOT IN (SELECT maTau FROM @TauTuyenDaDung WHERE maTuyenTau = @maTuyenTau);
        END
        ELSE IF @khoangCach <= 500 -- Tuyến trung: tàu du lịch, thống nhất
        BEGIN
            INSERT INTO @TauChon
            SELECT maTau, tenTau, loaiTau
            FROM Tau
            WHERE loaiTau IN ('tauDuLich', 'tauThongNhat') AND trangThaiTau = 'hoatDong'
            AND maTau NOT IN (SELECT maTau FROM @TauTuyenDaDung WHERE maTuyenTau = @maTuyenTau);
        END
        ELSE -- Tuyến dài: tàu chất lượng, thống nhất
        BEGIN
            INSERT INTO @TauChon
            SELECT maTau, tenTau, loaiTau
            FROM Tau
            WHERE loaiTau IN ('tauChatLuong', 'tauThongNhat') AND trangThaiTau = 'hoatDong'
            AND maTau NOT IN (SELECT maTau FROM @TauTuyenDaDung WHERE maTuyenTau = @maTuyenTau);
        END

        -- Tạo các chuyến tàu trong ngày cho tuyến
        SET @tripIndex = 1;
        WHILE @tripIndex <= @soChuyen
        BEGIN
            -- Kiểm tra xem còn tàu nào khả dụng không
            IF EXISTS (SELECT 1 FROM @TauChon)
            BEGIN
                -- Chọn ngẫu nhiên một tàu từ danh sách tàu khả dụng
                SELECT TOP 1 @maTau = maTau, @tenTau = tenTau
                FROM @TauChon
                ORDER BY NEWID();

                -- Lấy counter hiện tại cho tuyến
                SELECT @chuyenCounter = counter
                FROM @TuyenCounter
                WHERE maTuyenTau = @maTuyenTau;

                -- Tạo mã chuyến tàu: ddMMyyyy + AAAA (tên tàu) + TT + maTuyenTau + AAA
                DECLARE @tenTau4KyTu NVARCHAR(4);
                SET @tenTau4KyTu = LEFT(@tenTau + 'XXXX', 4);
                SET @maChuyenTau = CONCAT(
                    FORMAT(@currentDate, 'ddMMyyyy'),
                    @tenTau4KyTu,
                    'TT',
                    @maTuyenTau,
                    RIGHT('000' + CAST(@chuyenCounter AS VARCHAR(3)), 3)
                );

                -- Chọn giờ khởi hành ngẫu nhiên từ các khung giờ phổ biến
                SET @randomHour = FLOOR(RAND() * 8);
                SET @gioKhoiHanh = CASE @randomHour
                    WHEN 0 THEN '06:00:00'
                    WHEN 1 THEN '08:00:00'
                    WHEN 2 THEN '10:00:00'
                    WHEN 3 THEN '12:00:00'
                    WHEN 4 THEN '14:00:00'
                    WHEN 5 THEN '16:00:00'
                    WHEN 6 THEN '18:00:00'
                    WHEN 7 THEN '20:00:00'
                    ELSE '08:00:00'
                END;

                -- Tính thời gian dự kiến đến
                DECLARE @thoiGianChay FLOAT;
                DECLARE @tongPhut INT;
                DECLARE @ngayThem INT;
                DECLARE @gioThem INT;
                DECLARE @phutThem INT;

                -- Tính thời gian chạy dựa trên loại tàu và khoảng cách
                SELECT @thoiGianChay = CASE
                    WHEN EXISTS (SELECT 1 FROM @TauChon WHERE maTau = @maTau AND loaiTau = 'tauChatLuong') THEN @khoangCach / 70.0
                    WHEN EXISTS (SELECT 1 FROM @TauChon WHERE maTau = @maTau AND loaiTau = 'tauThongNhat') THEN @khoangCach / 60.0
                    WHEN EXISTS (SELECT 1 FROM @TauChon WHERE maTau = @maTau AND loaiTau = 'tauDuLich') THEN @khoangCach / 80.0
                    WHEN EXISTS (SELECT 1 FROM @TauChon WHERE maTau = @maTau AND loaiTau = 'tauDiaPhuong') THEN @khoangCach / 50.0
                    ELSE @khoangCach / 60.0
                END;

                -- Chuyển thời gian chạy thành phút
                SET @tongPhut = CEILING(@thoiGianChay * 60);
                SET @ngayThem = @tongPhut / (24 * 60);
                SET @gioThem = (@tongPhut % (24 * 60)) / 60;
                SET @phutThem = @tongPhut % 60;

                -- Tính ngày và giờ dự kiến đến
                SET @ngayDuKien = DATEADD(DAY, @ngayThem, @currentDate);
                SET @gioDuKien = DATEADD(MINUTE, @phutThem, DATEADD(HOUR, @gioThem, CAST(@gioKhoiHanh AS DATETIME)));

                -- Chèn dữ liệu vào bảng ChuyenTau
                INSERT INTO ChuyenTau (
                    maChuyenTau,
                    ngayKhoiHanh,
                    gioKhoiHanh,
                    ngayDuKien,
                    gioDuKien,
                    trangThaiChuyenTau,
                    maTau,
                    maTuyenTau
                ) VALUES (
                    @maChuyenTau,
                    @currentDate,
                    @gioKhoiHanh,
                    @ngayDuKien,
                    CAST(@gioDuKien AS TIME),
                    'hoatDong',
                    @maTau,
                    @maTuyenTau
                );

                -- Ghi lại tàu đã sử dụng cho tuyến này
                INSERT INTO @TauTuyenDaDung (maTau, maTuyenTau)
                VALUES (@maTau, @maTuyenTau);

                -- Xóa tàu vừa chọn khỏi danh sách khả dụng cho tuyến này
                DELETE FROM @TauChon WHERE maTau = @maTau;

                -- Cập nhật counter cho tuyến
                UPDATE @TuyenCounter
                SET counter = counter + 1
                WHERE maTuyenTau = @maTuyenTau;

                SET @tripIndex = @tripIndex + 1;
            END
            ELSE
            BEGIN
                -- Nếu không còn tàu khả dụng, thoát vòng lặp
                SET @tripIndex = @soChuyen + 1;
            END
        END;

        FETCH NEXT FROM tuyen_tau_cursor INTO @maTuyenTau, @khoangCach;
    END;

    CLOSE tuyen_tau_cursor;
    DEALLOCATE tuyen_tau_cursor;

    -- Tăng ngày lên 1
    SET @currentDate = DATEADD(DAY, 1, @currentDate);
END;

PRINT N'Đã tạo dữ liệu chuyến tàu thành công từ 27/05/2025 đến 02/06/2025 với định dạng mã chuyến tàu theo yêu cầu.';
GO

USE pioneer_station;
GO

-- Xóa dữ liệu hiện có trong bảng ChiTietCho (cẩn thận khi sử dụng trong môi trường production)
DELETE FROM ChiTietCho;
GO

-- Tạo bảng tạm để lưu danh sách chuyến tàu và chỗ
DECLARE @ChuyenTauCho TABLE (
    maChuyenTau VARCHAR(30),
    maCho VARCHAR(20),
    trangThaiCho NVARCHAR(20)
);

-- Lấy tất cả các cặp chuyến tàu và chỗ (cross join) với ngẫu nhiên cho mỗi hàng
INSERT INTO @ChuyenTauCho (maChuyenTau, maCho, trangThaiCho)
SELECT 
    ct.maChuyenTau,
    c.maCho,
    CASE 
        WHEN ABS(CHECKSUM(NEWID()) % 100) < 45 THEN 'daBan' -- 45% daBan
        ELSE 'conTrong' -- 55% conTrong
    END AS trangThaiCho
FROM ChuyenTau ct
CROSS JOIN Cho c
WHERE ct.trangThaiChuyenTau = 'hoatDong';

-- Chèn dữ liệu vào bảng ChiTietCho
INSERT INTO ChiTietCho (maChuyenTau, maCho, trangThaiCho)
SELECT maChuyenTau, maCho, trangThaiCho
FROM @ChuyenTauCho;

-- Kiểm tra số lượng bản ghi và tỷ lệ trạng thái
SELECT 
    COUNT(*) AS TongSoCho,
    SUM(CASE WHEN trangThaiCho = 'daBan' THEN 1 ELSE 0 END) AS SoChoDaBan,
    SUM(CASE WHEN trangThaiCho = 'conTrong' THEN 1 ELSE 0 END) AS SoChoConTrong,
    CAST(100.0 * SUM(CASE WHEN trangThaiCho = 'daBan' THEN 1 ELSE 0 END) / COUNT(*) AS DECIMAL(5,2)) AS TyLeDaBan,
    CAST(100.0 * SUM(CASE WHEN trangThaiCho = 'conTrong' THEN 1 ELSE 0 END) / COUNT(*) AS DECIMAL(5,2)) AS TyLeConTrong
FROM ChiTietCho;

PRINT N'Đã tạo dữ liệu cho bảng ChiTietCho với tỷ lệ khoảng 45% daBan và 55% conTrong.';
GO

USE pioneer_station;
GO
delete from ve;
go

-- Bước 1: Tạo bảng tạm để lưu thông tin vé
DECLARE @VeTam TABLE (
    maVe VARCHAR(30),
    ngayTaoVe DATE,
    trangThaiVe NVARCHAR(50),
    tenKhachHang NVARCHAR(100),
    cccd_HoChieu NVARCHAR(20),
    ngaySinh DATE,
    loaiVe NVARCHAR(50),
    maCho VARCHAR(20),
    maChuyenTau VARCHAR(30),
    khoangCach FLOAT,
    maHoaDonGoc NVARCHAR(30) -- Thêm cột tạm để nhóm vé vào hóa đơn
);

-- Bước 2: Tạo dữ liệu cho bảng Ve
WITH VeWithIndex AS (
    SELECT
        ctc.maChuyenTau,
        ctc.maCho,
        ct.ngayKhoiHanh,
        tt.loaiToa,
        tuyen.khoangCach,
        ROW_NUMBER() OVER (ORDER BY ctc.maChuyenTau, ctc.maCho) AS VeCounter
    FROM ChiTietCho ctc
    INNER JOIN ChuyenTau ct ON ctc.maChuyenTau = ct.maChuyenTau
    INNER JOIN TuyenTau tuyen ON ct.maTuyenTau = tuyen.maTuyenTau
    INNER JOIN Cho c ON ctc.maCho = c.maCho
    INNER JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'daBan'
)
INSERT INTO @VeTam (
    maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, maCho, maChuyenTau, khoangCach, maHoaDonGoc
)
SELECT
    -- Tạo mã vé: YYMMDD + TT + CCC + VE + NNNNNNN
    CONCAT(
        FORMAT(v.ngayKhoiHanh, 'yyMMdd'),
        RIGHT(v.maChuyenTau, 2),
        RIGHT(v.maCho, 3),
        'VE',
        RIGHT('0000000' + CAST(v.VeCounter AS VARCHAR(7)), 7)
    ) AS maVe,
    '2025-05-26' AS ngayTaoVe,
    'hieuLuc' AS trangThaiVe, -- Giá trị mặc định tuân thủ CHECK
    -- Tạo tên khách hàng giống người Việt
    CONCAT(
        CASE ABS(CHECKSUM(NEWID()) % 5)
            WHEN 0 THEN N'Nguyễn '
            WHEN 1 THEN N'Trần '
            WHEN 2 THEN N'Lê '
            WHEN 3 THEN N'Phạm '
            ELSE N'Hoàng '
        END,
        CASE ABS(CHECKSUM(NEWID()) % 3)
            WHEN 0 THEN N'Văn '
            WHEN 1 THEN N'Thị '
            ELSE N'Hồng '
        END,
        CASE ABS(CHECKSUM(NEWID()) % 10)
            WHEN 0 THEN N'An'
            WHEN 1 THEN N'Bình'
            WHEN 2 THEN N'Châu'
            WHEN 3 THEN N'Dũng'
            WHEN 4 THEN N'Hà'
            WHEN 5 THEN N'Lan'
            WHEN 6 THEN N'Mai'
            WHEN 7 THEN N'Nam'
            WHEN 8 THEN N'Nhung'
            ELSE N'Quyên'
        END
    ) AS tenKhachHang,
    -- Tạo CCCD ngẫu nhiên (12 chữ số)
    RIGHT('000000000000' + CAST(ABS(CHECKSUM(NEWID()) % 1000000000000) AS VARCHAR(12)), 12) AS cccd_HoChieu,
    -- Tạo ngày sinh ngẫu nhiên (1960-2010)
    DATEADD(
        DAY,
        ABS(CHECKSUM(NEWID()) % (DATEDIFF(DAY, '1960-01-01', '2010-12-31'))),
        '1960-01-01'
    ) AS ngaySinh,
    -- Chọn ngẫu nhiên loaiVe tuân thủ CHECK
    CASE ABS(CHECKSUM(NEWID()) % 4)
        WHEN 0 THEN 'nguoiLon'
        WHEN 1 THEN 'treEm'
        WHEN 2 THEN 'sinhVien'
        ELSE 'nguoiCaoTuoi'
    END AS loaiVe,
    v.maCho,
    v.maChuyenTau,
    -- Giới hạn khoảng cách tối đa 5000 km
    CASE
        WHEN v.khoangCach > 5000 THEN 5000
        ELSE v.khoangCach
    END AS khoangCach,
    -- Tạo mã hóa đơn gốc tạm thời để nhóm vé
    CONCAT('HDGOC', RIGHT('00000' + CAST(CEILING(v.VeCounter / 4.0) AS VARCHAR(5)), 5)) AS maHoaDonGoc
FROM VeWithIndex v
WHERE v.VeCounter <= 100; -- Giới hạn số lượng vé khoảng 100

-- Bước 3: Chèn dữ liệu vào bảng Ve
INSERT INTO Ve (
    maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, maHoaDon, maCho, maChuyenTau
)
SELECT
    maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, NULL AS maHoaDon, maCho, maChuyenTau
FROM @VeTam;

-- Bước 4: Tạo bảng tạm để lưu thông tin hóa đơn
DECLARE @HoaDonTam TABLE (
    maHoaDon VARCHAR(30),
    ngayTaoHoaDon DATE,
    gioTaoHoaDon TIME,
    phuongThucThanhToan NVARCHAR(50),
    phanTramGiamGia FLOAT,
    thanhTien FLOAT,
    tienKhachDua FLOAT,
    tienTraLai FLOAT,
    maKhachHang VARCHAR(20),
    maKhuyenMai VARCHAR(20),
    maNhanVien VARCHAR(20),
    maHoaDonGoc NVARCHAR(30)
);

-- Bước 5: Tạo dữ liệu cho bảng HoaDon
WITH GroupedVe AS (
    SELECT
        maHoaDonGoc,
        SUM(CASE
            WHEN loaiVe = 'nguoiLon' THEN (100000.0 * khoangCach / 100)
            ELSE (70000.0 * khoangCach / 100)
        END) AS TongGiaTriVeChuaGiamGia,
        MIN(maVe) AS RepresentativeMaVe, -- Lấy một mã vé đại diện để liên kết
        (SELECT TOP 1 maKhachHang FROM KhachHang WHERE trangThaiKhachHang = 'hoatDong' AND maKhachHang != 'KHVL' ORDER BY NEWID()) AS SelectedMaKhachHang,
        (SELECT TOP 1 maNhanVien FROM NhanVien WHERE trangThaiNhanVien = 'hoatDong' ORDER BY NEWID()) AS SelectedMaNhanVien
    FROM @VeTam
    GROUP BY maHoaDonGoc
),
HoaDonData AS (
    SELECT
        g.maHoaDonGoc,
        g.TongGiaTriVeChuaGiamGia,
        g.RepresentativeMaVe,
        g.SelectedMaKhachHang,
        g.SelectedMaNhanVien,
        -- Tạo số tự phát sinh duy nhất cho mỗi hóa đơn dựa trên nhân viên và ngày
        ROW_NUMBER() OVER (PARTITION BY g.SelectedMaNhanVien, FORMAT(GETDATE(), 'ddMMyyyy') ORDER BY g.maHoaDonGoc) AS UniqueSequence
    FROM GroupedVe g
)
INSERT INTO @HoaDonTam (
    maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai,
    maKhachHang, maKhuyenMai, maNhanVien, maHoaDonGoc
)
SELECT
    -- Tạo mã hóa đơn: ddMMyyyy + maNhanVien + XXXXXX
    CONCAT(
        FORMAT(GETDATE(), 'ddMMyyyy'), -- Ngày hiện tại
        hd.SelectedMaNhanVien,
        RIGHT('000000' + CAST(hd.UniqueSequence AS VARCHAR(6)), 6)
    ) AS maHoaDon,
    GETDATE() AS ngayTaoHoaDon, -- Ngày hiện tại
    CONVERT(TIME, GETDATE()) AS gioTaoHoaDon, -- Giờ hiện tại
    CASE
        WHEN ABS(CHECKSUM(NEWID()) % 2) = 0 THEN 'tienMat'
        ELSE 'chuyenKhoan'
    END AS phuongThucThanhToan,
    ISNULL(km.phanTramGiamGiaSuKien, 0) AS phanTramGiamGia,
    -- Tính thành tiền
    CAST(
        ROUND(
            hd.TongGiaTriVeChuaGiamGia * (1 - ISNULL(km.phanTramGiamGiaSuKien, 0)),
            0
        ) AS DECIMAL(18, 2)
    ) AS thanhTien,
    -- Tiền khách đưa: giả định trả đủ hoặc dư tối đa 10%
    CAST(
        ROUND(
            hd.TongGiaTriVeChuaGiamGia * (1 - ISNULL(km.phanTramGiamGiaSuKien, 0)) * (1 + (ABS(CHECKSUM(NEWID()) % 11) / 100.0)),
            0
        ) AS DECIMAL(18, 2)
    ) AS tienKhachDua,
    -- Tiền trả lại
    CAST(
        ROUND(
            hd.TongGiaTriVeChuaGiamGia * (1 - ISNULL(km.phanTramGiamGiaSuKien, 0)) * (1 + (ABS(CHECKSUM(NEWID()) % 11) / 100.0)),
            0
        ) AS DECIMAL(18, 2)
    ) -
    CAST(
        ROUND(
            hd.TongGiaTriVeChuaGiamGia * (1 - ISNULL(km.phanTramGiamGiaSuKien, 0)),
            0
        ) AS DECIMAL(18, 2)
    ) AS tienTraLai,
    hd.SelectedMaKhachHang AS maKhachHang,
    km.maKhuyenMai,
    hd.SelectedMaNhanVien AS maNhanVien,
    hd.maHoaDonGoc
FROM HoaDonData hd
LEFT JOIN KhuyenMai km ON (SELECT loaiKhachHang FROM KhachHang WHERE maKhachHang = hd.SelectedMaKhachHang) = km.loaiKhachHang
AND GETDATE() BETWEEN km.ngayBatDauSuKien AND km.ngayKetThucSuKien;

-- Bước 6: Chèn dữ liệu vào bảng HoaDon
INSERT INTO HoaDon (
    maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai,
    maKhachHang, maKhuyenMai, maNhanVien
)
SELECT
    maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai,
    maKhachHang, maKhuyenMai, maNhanVien
FROM @HoaDonTam;

-- Bước 7: Cập nhật maHoaDon vào bảng Ve
UPDATE v
SET maHoaDon = hdt.maHoaDon
FROM Ve v
INNER JOIN @VeTam vt ON v.maVe = vt.maVe
INNER JOIN @HoaDonTam hdt ON vt.maHoaDonGoc = hdt.maHoaDonGoc;

-- Bước 8: Kiểm tra kết quả
SELECT
    COUNT(*) AS TongSoVe,
    COUNT(DISTINCT maHoaDon) AS TongSoHoaDon
FROM Ve;

PRINT N'Đã tạo dữ liệu cho bảng Ve và HoaDon thành công với khoảng 100 vé và hóa đơn phù hợp.';
GO
