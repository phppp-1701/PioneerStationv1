
CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

CREATE TABLE NhanVien (
    maNhanVien VARCHAR(20) PRIMARY KEY,
    tenNhanVien NVARCHAR(50),
    cccd_HoChieu NVARCHAR(20) UNIQUE ,
    soDienThoai NVARCHAR(10),
    ngaySinh DATE,
    chucVu NVARCHAR(20) CHECK (chucVu IN ('banVe', 'quanLy')),
    gioiTinh NVARCHAR(5) CHECK (gioiTinh IN ('nam', 'nu')),
    urlAnh NVARCHAR(255),
    trangThaiNhanVien VARCHAR(20) CHECK (trangThaiNhanVien IN ('hoatDong','voHieuHoa')),
    email NVARCHAR(100)
);

CREATE TABLE TaiKhoan (
    tenTaiKhoan NVARCHAR(20) PRIMARY KEY,
    matKhau NVARCHAR(50),
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
);

CREATE TABLE CaLam (
    maCaLam VARCHAR(20) PRIMARY KEY,
    gioBatDau TIME,
    gioKetThuc TIME,
    ngay NVARCHAR(10) CHECK (ngay IN ('thuHai','thuBa','thuTu','thuNam','thuSau','thuBay','chuNhat'))
);

CREATE TABLE KhachHang (
    maKhachHang VARCHAR(20) PRIMARY KEY,
    tenKhachHang NVARCHAR(50),
    cccd_HoChieu NVARCHAR(20),
    soDienThoai NVARCHAR(10),
	loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('thanThiet', 'vip','vangLai')),
    trangThaiKhachHang VARCHAR(20) CHECK (trangThaiKhachHang IN ('hoatDong','voHieuHoa')),
    email NVARCHAR(100)
);

CREATE TABLE KhuyenMai (
    maKhuyenMai VARCHAR(20) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(100),
    ngayBatDauSuKien DATE,
    ngayKetThucSuKien DATE,
	loaiKhachHang VARCHAR(20) CHECK (loaiKhachHang IN ('thanThiet', 'vip','vangLai')),
    phanTramGiamGiaSuKien FLOAT
);

CREATE TABLE ChiTietCaLam (
    ngayLamViec DATE,
    gioBatDau TIME,
    gioKetThuc TIME,
    ghiChu NVARCHAR(255),
    trangThaiCaLam NVARCHAR(50) CHECK (trangThaiCaLam IN ('dangLam','daLam','chuaDenCa','khongLam')),
    maCaLam VARCHAR(20),
    maNhanVien VARCHAR(20),
    PRIMARY KEY (ngayLamViec, maCaLam, maNhanVien),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien),
	FOREIGN KEY (maCaLam) REFERENCES CaLam(maCaLam)
);

CREATE TABLE HoaDon (
    maHoaDon VARCHAR(30) PRIMARY KEY,
    ngayTaoHoaDon DATE,
    gioTaoHoaDon TIME,
    phuongThucThanhToan NVARCHAR(50) CHECK (phuongThucThanhToan IN ('tienMat','chuyenKhoan')),
    phanTramGiamGia FLOAT,
    thanhTien FLOAT,
    tienKhachDua FLOAT,
    tienTraLai FLOAT,
    maKhachHang VARCHAR(20),
    maKhuyenMai VARCHAR(20),
    ngayLamViec DATE,
    maCaLam VARCHAR(20),
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
    FOREIGN KEY (ngayLamViec, maCaLam, maNhanVien) REFERENCES ChiTietCaLam(ngayLamViec, maCaLam, maNhanVien)
);

CREATE TABLE Ve (
    maVe VARCHAR(30) PRIMARY KEY,
    ngayTaoVe DATE,
    trangThaiVe NVARCHAR(50) CHECK (trangThaiVe IN ('hieuLuc','daHuy','daDoi','hetHan')),
    tenKhachHang NVARCHAR(100),
    cccd_HoChieu NVARCHAR(20),
    ngaySinh DATE,
    loaiVe NVARCHAR(50) CHECK (loaiVe IN ('giuongNam','ngoiMem')),
    giaVe FLOAT,
    phanTramGiamGiaCoDinh FLOAT,
    maHoaDon VARCHAR(30),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon)
);

CREATE TABLE Ga (
    maGa VARCHAR(20) PRIMARY KEY,
    tenGa NVARCHAR(100),
    diaChi NVARCHAR(255)
);

CREATE TABLE TuyenTau (
    maTuyenTau VARCHAR(20) PRIMARY KEY,
    tenTuyenTau NVARCHAR(100),
    khoangCach FLOAT,
    gaDi VARCHAR(20),
    gaDen VARCHAR(20),
    FOREIGN KEY (gaDi) REFERENCES Ga(maGa),
    FOREIGN KEY (gaDen) REFERENCES Ga(maGa)
);

CREATE TABLE Tau (
    maTau VARCHAR(20) PRIMARY KEY,
    tenTau NVARCHAR(100),
    trangThaiTau NVARCHAR(50) CHECK (trangThaiTau IN ('hoatDong', 'khongHoatDong')),
    loaiTau NVARCHAR(50) CHECK (loaiTau IN ('tauChatLuong', 'tauThongNhat', 'tauDiaPhuong', 'tauDuLich'))
);


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
    maToaTau VARCHAR(20) PRIMARY KEY,
    thuTuToa INT,
    soLuongChoDaBan INT,
    soLuongChoDangDat INT,
    soLuongChoConTrong INT,
    maTau VARCHAR(20),
    FOREIGN KEY (maTau) REFERENCES Tau(maTau)
);

CREATE TABLE ToaGiuongNam (
    maToaTau VARCHAR(20) PRIMARY KEY,
    soHieuKhoang INT NOT NULL,
    soHieuTang INT NOT NULL,
    soLuongGiuong INT NOT NULL,
    FOREIGN KEY (maToaTau) REFERENCES ToaTau(maToaTau)
);

CREATE TABLE ToaNgoiMem (
    maToaTau VARCHAR(20) PRIMARY KEY,
    soLuongGhe INT NOT NULL,
    FOREIGN KEY (maToaTau) REFERENCES ToaTau(maToaTau)
);

CREATE TABLE Cho (
    maCho VARCHAR(20) PRIMARY KEY,
    soThuTuCho INT,
    maToaTau VARCHAR(20),
    FOREIGN KEY (maToaTau) REFERENCES ToaTau(maToaTau)
);

CREATE TABLE ChiTietCho (
    maCho VARCHAR(20),
	maChuyenTau VARCHAR(30),
	maVe VARCHAR(30),
    trangThaiCho NVARCHAR(50) CHECK (trangThaiCho IN ('daBan','dangDat','conTrong')),
	giaCho FLOAT,
    FOREIGN KEY (maCho) REFERENCES Cho(maCho),
	FOREIGN KEY (maChuyenTau) REFERENCES ChuyenTau(maChuyenTau),
	FOREIGN KEY (maVe) REFERENCES Ve(maVe)
);

INSERT INTO NhanVien (maNhanVien, tenNhanVien, cccd_HoChieu, soDienThoai, ngaySinh, chucVu, gioiTinh, urlAnh, trangThaiNhanVien, email) VALUES
('2023NV000001', N'Phạm Trương Hoàng Phương', '123456789012', '0901234567', '2004-01-17', 'quanLy', 'nam', 'image/phuong.jpg', 'hoatDong', 'phuong@gmail.com'),
('2023NV000002', N'Phạm Viết Quân', '987654321098', '0912345678', '1995-08-22', 'quanLy', 'nam', 'image/quan.jpg', 'hoatDong', 'quan@gmail.com'),
('2024NV000001', N'Bùi Tấn Quang Trung', '456789123456', '0923456789', '1988-03-10', 'quanLy', 'nam', 'image/trung.jpg', 'hoatDong', 'trung@gmail.com'),
('2024NV000002', N'Trần Minh Tuấn', '789123456789', '0934567890', '1992-11-30', 'quanLy', 'nam', 'image/tuan.jpg', 'hoatDong', 'tuan@gmail.com');
go

INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien) VALUES
('phuong123', 'matkhau1', '2023NV000001'),
('quan123', 'matkhau2', '2023NV000002'),
('trung123', 'matkhau3', '2024NV000001'),
('tuan123', 'matkhau4', '2024NV000002');
go

INSERT INTO CaLam (maCaLam, gioBatDau, gioKetThuc, ngay) VALUES
('CLV220525P01', '07:00:00', '11:00:00', 'thuHai'),
('CLV220525P02', '13:00:00', '17:00:00', 'thuHai'),
('CLV230525P01', '07:00:00', '11:00:00', 'thuBa'),
('CLV230525P02', '13:00:00', '17:00:00', 'thuBa');
go

INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
('2024KH000001', N'Nguyễn Văn Toàn', '001123456789', '0911111111', 'thanThiet', 'hoatDong', 'toan@gmail.com'),
('2024KH000002', N'Lê Thị Đào', '001234567891', '0922222222', 'vip', 'hoatDong', 'dao@gmail.com'),
('2024KH000003', N'Trần Văn An', '001345678912', '0933333333', 'vangLai', 'hoatDong', 'an@gmail.com');
go

INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, ngayBatDauSuKien, ngayKetThucSuKien, loaiKhachHang, phanTramGiamGiaSuKien) VALUES
('2025KM001', N'Khuyến mãi Tết', '2025-01-01', '2025-01-15', 'thanThiet', 10),
('2025KM002', N'Giảm giá hè', '2025-06-01', '2025-06-30', 'vip', 20);
go

INSERT INTO ChiTietCaLam (ngayLamViec, gioBatDau, gioKetThuc, ghiChu, trangThaiCaLam, maCaLam, maNhanVien) VALUES
('2025-05-22', '07:00:00', '11:00:00', N'Làm buổi sáng', 'dangLam', 'CLV220525P01', '2023NV000001'),
('2025-05-22', '13:00:00', '17:00:00', N'Làm buổi chiều', 'dangLam', 'CLV220525P02', '2023NV000002')
go

INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, ngayLamViec, maCaLam, maNhanVien) VALUES
('2023NV000001HD220520250007', '2025-05-22', '08:30:00', 'tienMat', 10, 90000, 100000, 10000, '2024KH000001', '2025KM001', '2025-05-22', 'CLV220525P01', '2023NV000001');
go

INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon) VALUES
('SE01GN0108143022052025', '2025-05-22', 'hieuLuc', N'Nguyễn Văn Toàn', '001123456789', '1990-01-01','giuongNam', 100000, 10, '2023NV000001HD220520250007');
go

INSERT INTO Ga (maGa, tenGa, diaChi) VALUES
('2020GA0001', N'Ga Hà Nội', N'120 Lê Duẩn, Hà Nội'),
('2020GA0002', N'Ga Sài Gòn', N'01 Nguyễn Thông, TP.HCM'),
('2020GA0003', N'Ga Đà Nẵng', N'791 Hải Phòng, Đà Nẵng');
go

INSERT INTO TuyenTau (maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen) VALUES
('2025TT0001', 'Hà Nội - Sài Gòn', 1726, '2020GA0001', '2020GA0002'),
('2025TT0002', 'Hà Nội - Đà Nẵng', 960, '2020GA0001', '2020GA0003');
go

INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
('2023SE01', N'SE01', 'hoatDong', 'tauThongNhat'),
('2023SE02', N'SE02', 'hoatDong', 'tauThongNhat'),
('2022SE03', N'SE03', 'hoatDong', 'tauChatLuong'),
('2022SE04', N'SE04', 'hoatDong', 'tauChatLuong'),
('2024DL01', N'DL01', 'hoatDong', 'tauDuLich'),
('2024DL02', N'DL02', 'hoatDong', 'tauDuLich'),
('2023DP01', N'DP01', 'hoatDong', 'tauDiaPhuong'),
('2023DP02', N'DP02', 'hoatDong', 'tauDiaPhuong');
go

INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau) VALUES
('2025SE000001', '2025-05-24', '06:00:00', '2025-05-25', '08:00:00', 'hoatDong', '2023SE01', '2025TT0001'),
('2025SE000002', '2025-05-24', '07:00:00', '2025-05-25', '09:00:00', 'hoatDong', '2023SE02', '2025TT0001'),
('2025SE000003', '2025-05-24', '08:00:00', '2025-05-25', '10:00:00', 'hoatDong', '2022SE03', '2025TT0001'),
('2025SE000004', '2025-05-24', '09:00:00', '2025-05-25', '11:00:00', 'hoatDong', '2022SE04', '2025TT0001'),
('2025SE000005', '2025-05-24', '10:00:00', '2025-05-25', '12:00:00', 'hoatDong', '2024DL01', '2025TT0001'),
('2025SE000006', '2025-05-24', '11:00:00', '2025-05-25', '13:00:00', 'hoatDong', '2024DL02', '2025TT0001'),
('2025SE000007', '2025-05-24', '12:00:00', '2025-05-25', '14:00:00', 'hoatDong', '2023DP01', '2025TT0001'),
('2025SE000008', '2025-05-24', '13:00:00', '2025-05-25', '15:00:00', 'hoatDong', '2023DP02', '2025TT0001'),
('2025SE000009', '2025-05-24', '14:00:00', '2025-05-25', '16:00:00', 'hoatDong', '2023SE01', '2025TT0001'),
('2025SE000010', '2025-05-24', '15:00:00', '2025-05-25', '17:00:00', 'hoatDong', '2023SE02', '2025TT0001');
go

INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau) VALUES
('2025SE000011', '2025-05-24', '06:00:00', '2025-05-26', '08:00:00', 'hoatDong', '2023SE01', '2025TT0001'),
('2025SE000012', '2025-05-24', '07:00:00', '2025-05-26', '09:00:00', 'hoatDong', '2023SE02', '2025TT0001'),
('2025SE000013', '2025-05-24', '08:00:00', '2025-05-26', '10:00:00', 'hoatDong', '2022SE03', '2025TT0001'),
('2025SE000014', '2025-05-24', '09:00:00', '2025-05-26', '11:00:00', 'hoatDong', '2022SE04', '2025TT0001'),
('2025SE000015', '2025-05-24', '10:00:00', '2025-05-26', '12:00:00', 'hoatDong', '2024DL01', '2025TT0001'),
('2025SE000016', '2025-05-24', '11:00:00', '2025-05-26', '13:00:00', 'hoatDong', '2024DL02', '2025TT0001'),
('2025SE000017', '2025-05-24', '12:00:00', '2025-05-26', '14:00:00', 'hoatDong', '2023DP01', '2025TT0001'),
('2025SE000018', '2025-05-24', '13:00:00', '2025-05-26', '15:00:00', 'hoatDong', '2023DP02', '2025TT0001'),
('2025SE000019', '2025-05-24', '14:00:00', '2025-05-26', '16:00:00', 'hoatDong', '2023SE01', '2025TT0001'),
('2025SE000020', '2025-05-24', '15:00:00', '2025-05-26', '17:00:00', 'hoatDong', '2023SE02', '2025TT0001');
go

INSERT INTO ToaTau (maToaTau, thuTuToa, soLuongChoDaBan, soLuongChoDangDat, soLuongChoConTrong, maTau) VALUES
('2025T01', 1, 10, 2, 18, 'SE01GN'),
('2025T02', 2, 10, 2, 18, 'SE01GN');
go

INSERT INTO ToaGiuongNam (maToaTau, soHieuKhoang, soHieuTang, soLuongGiuong) VALUES
('2025T01', 6, 2, 12);
go

INSERT INTO ToaNgoiMem (maToaTau, soLuongGhe) VALUES
('2025T01', 12);
go

INSERT INTO Cho (maCho, soThuTuCho, maToaTau) VALUES
('SE01GN0108', 1, '2025T01'),
('SE01GN0109', 2, '2025T01');
go

INSERT INTO ChiTietCho (maCho, maChuyenTau, maVe, trangThaiCho, giaCho) VALUES
('SE01GN0108', '2025SE000001', 'SE01GN0108143022052025', 'daBan', 90000);
go