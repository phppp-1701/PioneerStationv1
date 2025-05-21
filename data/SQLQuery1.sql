
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
    trangThaiCaLam NVARCHAR(50),
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
    phuongThucThanhToan NVARCHAR(50),
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
    trangThaiVe NVARCHAR(50),
    tenKhachHang NVARCHAR(100),
    cccd_HoChieu NVARCHAR(20),
    ngaySinh DATE,
    loaiVe NVARCHAR(50),
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
    trangThaiTau NVARCHAR(50)
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

go
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
