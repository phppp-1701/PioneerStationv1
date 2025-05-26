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
    loaiVe NVARCHAR(50) CHECK (loaiVe IN ('giuongNam','ngoiMem')),
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

