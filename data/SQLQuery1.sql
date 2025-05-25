CREATE DATABASE pioneer_station;
GO

USE pioneer_station;
GO

-- Create tables
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

CREATE TABLE TaiKhoan (
    tenTaiKhoan NVARCHAR(20) PRIMARY KEY,
    matKhau NVARCHAR(50),
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maNhanVien) REFERENCES NhanVien(maNhanVien)
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
		maTau VARCHAR(20),
		loaiToa VARCHAR(20) NOT NULL,
		soHieuKhoang INT,
		soHieuTang INT,
		soLuongGiuong INT,
		soLuongGhe INT,
		FOREIGN KEY (maTau) REFERENCES Tau(maTau),
		CONSTRAINT chk_loaiToa CHECK (loaiToa IN ('giuongNam', 'ngoiMem')),
		CONSTRAINT chk_toa_attributes CHECK (
			(loaiToa = 'giuongNam' AND soHieuKhoang IS NOT NULL AND soHieuKhoang > 0 
			 AND soHieuTang IS NOT NULL AND soHieuTang > 0 
			 AND soLuongGiuong IS NOT NULL AND soLuongGiuong > 0 
			 AND soLuongGhe IS NULL)
			OR
			(loaiToa = 'ngoiMem' AND soLuongGhe IS NOT NULL AND soLuongGhe > 0 
			 AND soHieuKhoang IS NULL AND soHieuTang IS NULL AND soLuongGiuong IS NULL)
		),
		CONSTRAINT chk_non_negative CHECK (thuTuToa >= 0)
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
    trangThaiCho NVARCHAR(50) CHECK (trangThaiCho IN ('daBan','dangDat','conTrong')),
    giaCho FLOAT,
    PRIMARY KEY (maCho, maChuyenTau),
    FOREIGN KEY (maCho) REFERENCES Cho(maCho),
    FOREIGN KEY (maChuyenTau) REFERENCES ChuyenTau(maChuyenTau)
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
    maNhanVien VARCHAR(20),
    FOREIGN KEY (maKhachHang) REFERENCES KhachHang(maKhachHang),
    FOREIGN KEY (maKhuyenMai) REFERENCES KhuyenMai(maKhuyenMai),
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
    maCho VARCHAR(20),
    maChuyenTau VARCHAR(30),
    FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    FOREIGN KEY (maCho, maChuyenTau) REFERENCES ChiTietCho(maCho, maChuyenTau)
);

-- Insert data
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

-- Insert 'thanThiet' customers (approx. 10 records)
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KH000001'), N'Nguyễn Thanh Hải', '079199212345', '0901112223', 'thanThiet', 'hoatDong', 'hai.nguyen@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000002'), N'Trần Bích Ngọc', '079199567890', '0912223334', 'thanThiet', 'hoatDong', 'ngoc.tran@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000003'), N'Lê Văn Đạt', '079198801234', '0863334445', 'thanThiet', 'hoatDong', 'dat.le@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000004'), N'Phạm Thị Mai Phương', '079200056789', '0384445556', 'thanThiet', 'hoatDong', 'maiphuong.pham@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000005'), N'Đỗ Quốc Anh', '079199098765', '0975556667', 'thanThiet', 'hoatDong', 'quocanh.do@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000006'), N'Hoàng Việt Đức', '079199312345', '0966667778', 'thanThiet', 'hoatDong', 'vietduc.hoang@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000007'), N'Vũ Thị Thùy Linh', '079199789012', '0937778889', 'thanThiet', 'hoatDong', 'thuylinh.vu@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000008'), N'Bùi Quang Trung', '079198754321', '0898889990', 'thanThiet', 'hoatDong', 'quangtrung.bui@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000009'), N'Dương Ánh Tuyết', '079200223456', '0709990001', 'thanThiet', 'hoatDong', 'tuyet.duong@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000010'), N'Nguyễn Thanh Duy', '079199834567', '0761112223', 'thanThiet', 'hoatDong', 'thanhduy.nguyen@email.com');
GO

-- Insert 'vip' customers (approx. 4 records)
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KH000011'), N'Phan Ngọc Bích', '079198011223', '0982345678', 'vip', 'hoatDong', 'ngocbich.phan@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000012'), N'Trần Hữu Khang', '079197544556', '0913456789', 'vip', 'hoatDong', 'huukhang.tran@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000013'), N'Lý Thùy Dung', '079198377889', '0944567890', 'vip', 'hoatDong', 'thuydung.ly@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000014'), N'Vũ Thanh Sang', '079197299001', '0885678901', 'vip', 'hoatDong', 'thanhsang.vu@vipmail.com');
GO

-- Insert the single 'vangLai' customer record
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KHVL0001'), N'Khách Vãng Lai', NULL, NULL, 'vangLai', 'hoatDong', NULL);
GO

INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, ngayBatDauSuKien, ngayKetThucSuKien, loaiKhachHang, phanTramGiamGiaSuKien) VALUES
(CONCAT(YEAR(GETDATE()), 'KM001'), N'Khuyến mãi hè sôi động', '2025-06-01', '2025-08-31', 'thanThiet', 0.10),
(CONCAT(YEAR(GETDATE()), 'KM002'), N'Ưu đãi VIP đặc biệt', '2025-05-20', '2025-12-31', 'vip', 0.15),
(CONCAT(YEAR(GETDATE()), 'KM003'), N'Giảm giá khách vãng lai', '2025-05-25', '2025-06-30', 'vangLai', 0.05),
(CONCAT(YEAR(GETDATE()), 'KM004'), N'Chào đón năm mới', '2025-01-01', '2025-01-31', 'thanThiet', 0.08),
(CONCAT(YEAR(GETDATE()), 'KM005'), N'Quốc Khánh 2/9', '2025-09-01', '2025-09-05', 'vip', 0.20),
(CONCAT(YEAR(GETDATE()), 'KM006'), N'Ngày phụ nữ Việt Nam', '2025-10-20', '2025-10-20', 'thanThiet', 0.12);
GO

-- Create a new sequence for maGa to ensure auto-incrementing ABCD suffix
IF OBJECT_ID('SeqGa', 'SO') IS NOT NULL
    DROP SEQUENCE SeqGa;
GO
CREATE SEQUENCE SeqGa
    START WITH 1
    INCREMENT BY 1;
GO

-- Insert sample station data following the maGa format: XXXXGAABCD
-- XXXX is current year (2025), GA is fixed, ABCD is auto-incrementing number

INSERT INTO Ga (maGa, tenGa, diaChi) VALUES
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Sài Gòn', N'Số 1 Nguyễn Thông, Phường 9, Quận 3, TP. Hồ Chí Minh'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Hà Nội', N'120 Lê Duẩn, Cửa Nam, Hoàn Kiếm, Hà Nội'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Đà Nẵng', N'791 Hải Phòng, Thanh Khê, Đà Nẵng'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Huế', N'02 Bùi Thị Xuân, Phường Đúc, TP. Huế, Thừa Thiên Huế'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Nha Trang', N'17 Thái Nguyên, Phước Tân, TP. Nha Trang, Khánh Hòa'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Vinh', N'1 Phan Bội Châu, Lê Lợi, TP. Vinh, Nghệ An'),
(CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000')), N'Ga Đồng Hới', N'Tiểu khu 4, Nam Lý, TP. Đồng Hới, Quảng Bình');
GO

-- Create a new sequence for maTuyenTau to ensure auto-incrementing ABCD suffix
IF OBJECT_ID('SeqTuyenTau', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTuyenTau;
GO
CREATE SEQUENCE SeqTuyenTau
    START WITH 1
    INCREMENT BY 1;
GO

-- Declare variables to hold maGa values
DECLARE @sgMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Sài Gòn');
DECLARE @hnMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Hà Nội');
DECLARE @dnMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Đà Nẵng');
DECLARE @ntMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Nha Trang');
DECLARE @hueMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Huế');
DECLARE @vinhMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Vinh');
DECLARE @dhMaGa VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Đồng Hới');

-- Insert sample route data following the maTuyenTau format: XXXXTTABCD
-- XXXX is current year (2025), TT is fixed, ABCD is auto-incrementing number

INSERT INTO TuyenTau (maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen) VALUES
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Hà Nội', 1726.0, @sgMaGa, @hnMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Sài Gòn', 1726.0, @hnMaGa, @sgMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Đà Nẵng', 961.0, @sgMaGa, @dnMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đà Nẵng - Sài Gòn', 961.0, @dnMaGa, @sgMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Huế', 688.0, @hnMaGa, @hueMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Huế - Hà Nội', 688.0, @hueMaGa, @hnMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Nha Trang', 411.0, @sgMaGa, @ntMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Nha Trang - Sài Gòn', 411.0, @ntMaGa, @sgMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Vinh', 319.0, @hnMaGa, @vinhMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Vinh - Hà Nội', 319.0, @vinhMaGa, @hnMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đà Nẵng - Đồng Hới', 268.0, @dnMaGa, @dhMaGa),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đồng Hới - Đà Nẵng', 268.0, @dhMaGa, @dnMaGa);
GO

-- Create sequences for the sequential part of maTau for each type
IF OBJECT_ID('SeqTauSE', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauSE;
GO
CREATE SEQUENCE SeqTauSE
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTauDP', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauDP;
GO
CREATE SEQUENCE SeqTauDP
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTauDL', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauDL;
GO
CREATE SEQUENCE SeqTauDL
    START WITH 1
    INCREMENT BY 1;
GO

-- Insert 'tauChatLuong' (High Quality Train) - 2 trains
-- maTau: XXXXSE01, XXXXSE02
INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
(CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), N'SE01', 'hoatDong', 'tauChatLuong'),
(CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), N'SE02', 'hoatDong', 'tauChatLuong');
GO

-- Insert 'tauThongNhat' (Unified Train) - Approximately 25-30 trains (SE03 to SE27/SE32)
-- maTau: XXXXSE03 onwards
DECLARE @i_se INT = 3;
WHILE @i_se <= 30
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), CONCAT(N'SE', FORMAT(@i_se, '00')), 
     CASE WHEN @i_se % 5 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END, -- Every 5th train is inactive
     'tauThongNhat');
    SET @i_se = @i_se + 1;
END;
GO

-- Insert 'tauDiaPhuong' (Local Train) - Approximately 10-15 trains (DP01 to DP15)
-- maTau: XXXXDP01 onwards
DECLARE @i_dp INT = 1;
WHILE @i_dp <= 15
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'DP', FORMAT(NEXT VALUE FOR SeqTauDP, '00')), CONCAT(N'DP', FORMAT(@i_dp, '00')), 
     CASE WHEN @i_dp % 4 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END, -- Every 4th train is inactive
     'tauDiaPhuong');
    SET @i_dp = @i_dp + 1;
END;
GO

-- Insert 'tauDuLich' (Tourist Train) - Approximately 5-10 trains (DL01 to DL10)
-- maTau: XXXXDL01 onwards
DECLARE @i_dl INT = 1;
WHILE @i_dl <= 10
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'DL', FORMAT(NEXT VALUE FOR SeqTauDL, '00')), CONCAT(N'DL', FORMAT(@i_dl, '00')), 
     CASE WHEN @i_dl % 3 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END, -- Every 3rd train is inactive
     'tauDuLich');
    SET @i_dl = @i_dl + 1;
END;
GO
-- Create a new sequence for maChuyenTau to ensure unique, auto-incrementing suffix
IF OBJECT_ID('SeqChuyenTau', 'SO') IS NOT NULL
    DROP SEQUENCE SeqChuyenTau;
GO
CREATE SEQUENCE SeqChuyenTau
    START WITH 1
    INCREMENT BY 1;
GO

-- Declare variables for date range
DECLARE @startDate DATE = GETDATE(); -- Ngày bắt đầu: hôm nay
DECLARE @endDate DATE = DATEADD(week, 1, @startDate); -- Ngày kết thúc: 1 tuần sau
DECLARE @currentDate DATE;
DECLARE @numTripsForDay INT;
DECLARE @tripIndex INT;
DECLARE @gioKhoiHanh TIME;

-- Temporary table to hold active trains and their types
DECLARE @activeTrains TABLE (maTau VARCHAR(20), loaiTau NVARCHAR(50));
INSERT INTO @activeTrains SELECT maTau, loaiTau FROM Tau WHERE trangThaiTau = 'hoatDong';

-- Cursor to iterate through each TuyenTau
DECLARE tuyentau_cursor CURSOR FOR
SELECT maTuyenTau FROM TuyenTau;

DECLARE @maTuyenTauLoop VARCHAR(20);

OPEN tuyentau_cursor;

FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @currentDate = @startDate;
    WHILE @currentDate <= @endDate
    BEGIN
        -- Reset available trains for the current day
        DECLARE @dailyAvailableTrains TABLE (maTau VARCHAR(20), loaiTau NVARCHAR(50));
        INSERT INTO @dailyAvailableTrains SELECT maTau, loaiTau FROM @activeTrains;

        SET @numTripsForDay = CAST(RAND() * 3 + 4 AS INT); -- Randomly 4 to 6 trips per day for each route
        SET @tripIndex = 1;

        WHILE @tripIndex <= @numTripsForDay
        BEGIN
            DECLARE @selectedTau VARCHAR(20);
            DECLARE @selectedLoaiTau NVARCHAR(50);

            -- Select a random available train for this trip on this day
            SELECT TOP 1 @selectedTau = maTau, @selectedLoaiTau = loaiTau
            FROM @dailyAvailableTrains
            ORDER BY NEWID();

            IF @selectedTau IS NOT NULL
            BEGIN
                -- Remove the selected train from the daily available list
                DELETE FROM @dailyAvailableTrains WHERE maTau = @selectedTau;

                -- Generate departure time (staggered for realism)
                SET @gioKhoiHanh = DATEADD(hour, (6 + (@tripIndex - 1) * 3) % 24, '00:00:00'); -- Example: 6:00, 9:00, 12:00, 15:00, 18:00, 21:00
                SET @gioKhoiHanh = DATEADD(minute, CAST(RAND() * 59 AS INT), @gioKhoiHanh); -- Add random minutes

                -- Determine loaiTau abbreviation for maChuyenTau
                DECLARE @loaiTauAbbr VARCHAR(2);
                IF @selectedLoaiTau = 'tauChatLuong' OR @selectedLoaiTau = 'tauThongNhat' SET @loaiTauAbbr = 'SE';
                ELSE IF @selectedLoaiTau = 'tauDiaPhuong' SET @loaiTauAbbr = 'DP';
                ELSE IF @selectedLoaiTau = 'tauDuLich' SET @loaiTauAbbr = 'DL';
                ELSE SET @loaiTauAbbr = 'XX'; -- Fallback

                -- Construct maChuyenTau: YYYYABXXXXXX
                DECLARE @maChuyenTauNew VARCHAR(30) = CONCAT(YEAR(@currentDate), @loaiTauAbbr, FORMAT(NEXT VALUE FOR SeqChuyenTau, '000000'));

                -- Calculate estimated arrival date and time
                DECLARE @tripDurationHours INT = 12 + CAST(RAND() * 12 AS INT); -- Duration 12-24 hours
                DECLARE @arrivalDateTime DATETIME = DATEADD(hour, @tripDurationHours, CAST(@currentDate AS DATETIME) + CAST(@gioKhoiHanh AS DATETIME));
                
                DECLARE @ngayDuKien DATE = CAST(@arrivalDateTime AS DATE);
                DECLARE @gioDuKien TIME = CAST(@arrivalDateTime AS TIME);

                INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau)
                VALUES (
                    @maChuyenTauNew,
                    @currentDate,
                    @gioKhoiHanh,
                    @ngayDuKien, -- Corrected: Use the calculated date part
                    @gioDuKien, -- Corrected: Use the calculated time part
                    'hoatDong',
                    @selectedTau,
                    @maTuyenTauLoop
                );
            END
            ELSE
            BEGIN
                -- Not enough unique trains for this day/route, break from inner loop
                BREAK;
            END
            SET @tripIndex = @tripIndex + 1;
        END
        SET @currentDate = DATEADD(day, 1, @currentDate);
    END
    FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;
END;

CLOSE tuyentau_cursor;
DEALLOCATE tuyentau_cursor;
GO

-- Regenerate ChiTietCho (Seat Details) for newly generated ChuyenTau
DECLARE @currentMaChuyenTau VARCHAR(30);
DECLARE @currentMaTauForCTC VARCHAR(20);
DECLARE @khoangCachForCTC FLOAT;
DECLARE @maToaTauForCTC VARCHAR(20);
DECLARE @giaChoForCTC FLOAT;
DECLARE @loaiToaForCTC NVARCHAR(20);

DECLARE chitietcho_cursor CURSOR FOR
SELECT ct.maChuyenTau, ct.maTau, tt.khoangCach, toa.maToaTau, toa.loaiToa
FROM ChuyenTau ct
JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau
JOIN Tau t ON ct.maTau = t.maTau
JOIN ToaTau toa ON t.maTau = toa.maTau;

OPEN chitietcho_cursor;

FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Determine giaCho based on loaiToa and khoangCach
    IF @loaiToaForCTC = 'ngoiMem'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 200000;
    END
    ELSE IF @loaiToaForCTC = 'giuongNam'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 300000;
    END
    ELSE
    BEGIN
        SET @giaChoForCTC = 0; -- Default or error value
    END

    -- Insert all seats for this train car and trip as 'conTrong' (available)
    INSERT INTO ChiTietCho (maCho, maChuyenTau, trangThaiCho, giaCho)
    SELECT c.maCho, @currentMaChuyenTau, 'conTrong', @giaChoForCTC
    FROM Cho c
    WHERE c.maToaTau = @maToaTauForCTC;

    FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;
END;

CLOSE chitietcho_cursor;
DEALLOCATE chitietcho_cursor;
GO

-- Sample HoaDon and Ve data (simplified for illustration)
-- This section demonstrates how to create a single transaction for one of the newly generated trips.

-- Find an available seat from the newly generated data
DECLARE @sampleMaChuyenTauNew VARCHAR(30);
DECLARE @sampleMaChoNew VARCHAR(20);
DECLARE @sampleGiaChoNew FLOAT;

SELECT TOP 1 @sampleMaChuyenTauNew = ctc.maChuyenTau, @sampleMaChoNew = ctc.maCho, @sampleGiaChoNew = ctc.giaCho
FROM ChiTietCho ctc
WHERE ctc.trangThaiCho = 'conTrong'
ORDER BY NEWID(); -- Pick a random available seat

IF @sampleMaChuyenTauNew IS NOT NULL
BEGIN
    DECLARE @maNhanVienBanVeSample VARCHAR(20) = '2023NV000001'; -- Assuming a 'banVe' employee
    DECLARE @maKhachHangThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhachHangThanThietSample = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' customer

    DECLARE @maKhuyenMaiThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiThanThietSample = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' promotion

    -- Generate a unique HoaDon ID
    DECLARE @maHoaDonNew VARCHAR(30) = @maNhanVienBanVeSample + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');

    -- Calculate thanhTien with a sample discount
    DECLARE @phanTramGiamGiaHD FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiThanThietSample);
    DECLARE @thanhTienHD FLOAT = @sampleGiaChoNew * (1 - @phanTramGiamGiaHD);
    DECLARE @tienKhachDuaHD FLOAT = @thanhTienHD + 50000; -- Assume customer pays slightly more
    DECLARE @tienTraLaiHD FLOAT = @tienKhachDuaHD - @thanhTienHD;

    INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
    (@maHoaDonNew, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD, @thanhTienHD, @tienKhachDuaHD, @tienTraLaiHD, @maKhachHangThanThietSample, @maKhuyenMaiThanThietSample, @maNhanVienBanVeSample);

    -- Get customer details for the ticket
    DECLARE @tenKhachHangVe NVARCHAR(50);
    DECLARE @cccd_HoChieuVe NVARCHAR(20);
    DECLARE @ngaySinhVe DATE;
    DECLARE @loaiVe NVARCHAR(50);

    SELECT @tenKhachHangVe = tenKhachHang, @cccd_HoChieuVe = cccd_HoChieu
    FROM KhachHang
    WHERE maKhachHang = @maKhachHangThanThietSample;

    -- For sample data, using a fixed birthday or employee's birthday for simplicity
    SET @ngaySinhVe = '1990-01-01';

    -- Determine loaiVe from ToaTau linked to maCho
    SELECT @loaiVe = tt.loaiToa
    FROM Cho c
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE c.maCho = @sampleMaChoNew;

    -- Generate a unique Ve ID: maCho + HHmmss + DDMMYY
    DECLARE @maVeNew VARCHAR(30) = @sampleMaChoNew + FORMAT(GETDATE(), 'HHmmss') + FORMAT(GETDATE(), 'ddMMyy');

    INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
    (@maVeNew, GETDATE(), 'hieuLuc', @tenKhachHangVe, @cccd_HoChieuVe, @ngaySinhVe, @loaiVe, @sampleGiaChoNew, 0.00, @maHoaDonNew, @sampleMaChoNew, @sampleMaChuyenTauNew);

    -- Update ChiTietCho status to 'daBan'
    UPDATE ChiTietCho
    SET trangThaiCho = 'daBan'
    WHERE maCho = @sampleMaChoNew AND maChuyenTau = @sampleMaChuyenTauNew;
END
GO

-- Create a new sequence for maChuyenTau to ensure unique, auto-incrementing suffix
IF OBJECT_ID('SeqChuyenTau', 'SO') IS NOT NULL
    DROP SEQUENCE SeqChuyenTau;
GO
CREATE SEQUENCE SeqChuyenTau
    START WITH 1
    INCREMENT BY 1;
GO

-- Insert data for ToaTau (Train Cars) based on existing Tau data
DECLARE @currentMaTau VARCHAR(20);
DECLARE @currentLoaiTau NVARCHAR(50);
DECLARE @thuTuToa INT;

-- Cursor to iterate through each Tau (train)
DECLARE tau_cursor CURSOR FOR
SELECT maTau, loaiTau FROM Tau;

OPEN tau_cursor;

FETCH NEXT FROM tau_cursor INTO @currentMaTau, @currentLoaiTau;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @thuTuToa = 1;

    IF @currentLoaiTau = 'tauChatLuong' -- SE01, SE02
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
    END
    ELSE IF @currentLoaiTau = 'tauThongNhat' -- SE03 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 8, 3, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 8, 3, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
    END
    ELSE IF @currentLoaiTau = 'tauDiaPhuong' -- DP01 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 80);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 80);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 4, 2, 8, NULL);
    END
    ELSE IF @currentLoaiTau = 'tauDuLich' -- DL01 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 50);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 50);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
    END

    FETCH NEXT FROM tau_cursor INTO @currentMaTau, @currentLoaiTau;
END;

CLOSE tau_cursor;
DEALLOCATE tau_cursor;
GO

-- Declare variables for date range
DECLARE @startDate DATE = GETDATE(); -- Ngày bắt đầu: hôm nay
DECLARE @endDate DATE = DATEADD(week, 1, @startDate); -- Ngày kết thúc: 1 tuần sau
DECLARE @currentDate DATE;
DECLARE @numTripsForDay INT;
DECLARE @tripIndex INT;
DECLARE @gioKhoiHanh TIME;

-- Temporary table to hold active trains and their types
DECLARE @activeTrains TABLE (maTau VARCHAR(20), loaiTau NVARCHAR(50));
INSERT INTO @activeTrains SELECT maTau, loaiTau FROM Tau WHERE trangThaiTau = 'hoatDong';

-- Cursor to iterate through each TuyenTau
DECLARE tuyentau_cursor CURSOR FOR
SELECT maTuyenTau FROM TuyenTau;

DECLARE @maTuyenTauLoop VARCHAR(20);

OPEN tuyentau_cursor;

FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @currentDate = @startDate;
    WHILE @currentDate <= @endDate
    BEGIN
        -- Reset available trains for the current day
        DELETE FROM @dailyAvailableTrains; -- Clear for new day
        INSERT INTO @dailyAvailableTrains SELECT maTau, loaiTau FROM @activeTrains;

        SET @numTripsForDay = CAST(RAND() * 3 + 4 AS INT); -- Randomly 4 to 6 trips per day for each route
        SET @tripIndex = 1;

        WHILE @tripIndex <= @numTripsForDay
        BEGIN
            DECLARE @selectedTau VARCHAR(20);
            DECLARE @selectedLoaiTau NVARCHAR(50);

            -- Select a random available train for this trip on this day
            SELECT TOP 1 @selectedTau = maTau, @selectedLoaiTau = loaiTau
            FROM @dailyAvailableTrains
            ORDER BY NEWID();

            IF @selectedTau IS NOT NULL
            BEGIN
                -- Remove the selected train from the daily available list
                DELETE FROM @dailyAvailableTrains WHERE maTau = @selectedTau;

                -- Generate departure time (staggered for realism)
                SET @gioKhoiHanh = DATEADD(hour, (6 + (@tripIndex - 1) * 3) % 24, '00:00:00'); -- Example: 6:00, 9:00, 12:00, 15:00, 18:00, 21:00
                SET @gioKhoiHanh = DATEADD(minute, CAST(RAND() * 59 AS INT), @gioKhoiHanh); -- Add random minutes

                -- Determine loaiTau abbreviation for maChuyenTau
                DECLARE @loaiTauAbbr VARCHAR(2);
                IF @selectedLoaiTau = 'tauChatLuong' OR @selectedLoaiTau = 'tauThongNhat' SET @loaiTauAbbr = 'SE';
                ELSE IF @selectedLoaiTau = 'tauDiaPhuong' SET @loaiTauAbbr = 'DP';
                ELSE IF @selectedLoaiTau = 'tauDuLich' SET @loaiTauAbbr = 'DL';
                ELSE SET @loaiTauAbbr = 'XX'; -- Fallback

                -- Construct maChuyenTau:
                DECLARE @maChuyenTauNew VARCHAR(30) = CONCAT(YEAR(@currentDate), @loaiTauAbbr, FORMAT(NEXT VALUE FOR SeqChuyenTau, '000000'));

                -- Calculate estimated arrival date and time
                DECLARE @tripDurationHours INT = 12 + CAST(RAND() * 12 AS INT); -- Duration 12-24 hours
                DECLARE @arrivalDateTime DATETIME = DATEADD(hour, @tripDurationHours, CAST(@currentDate AS DATETIME) + CAST(@gioKhoiHanh AS DATETIME));
                
                DECLARE @ngayDuKien DATE = CAST(@arrivalDateTime AS DATE);
                DECLARE @gioDuKien TIME = CAST(@arrivalDateTime AS TIME);

                INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau)
                VALUES (
                    @maChuyenTauNew,
                    @currentDate,
                    @gioKhoiHanh,
                    @ngayDuKien,
                    @gioDuKien,
                    'hoatDong',
                    @selectedTau,
                    @maTuyenTauLoop
                );
            END
            ELSE
            BEGIN
                -- Not enough unique trains for this day/route, break from inner loop
                BREAK;
            END
            SET @tripIndex = @tripIndex + 1;
        END
        SET @currentDate = DATEADD(day, 1, @currentDate);
    END
    FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;
END;

CLOSE tuyentau_cursor;
DEALLOCATE tuyentau_cursor;
GO

-- Regenerate ChiTietCho (Seat Details) for newly generated ChuyenTau
DECLARE @currentMaChuyenTau VARCHAR(30);
DECLARE @currentMaTauForCTC VARCHAR(20);
DECLARE @khoangCachForCTC FLOAT;
DECLARE @maToaTauForCTC VARCHAR(20);
DECLARE @giaChoForCTC FLOAT;
DECLARE @loaiToaForCTC NVARCHAR(20);

DECLARE chitietcho_cursor CURSOR FOR
SELECT ct.maChuyenTau, ct.maTau, tt.khoangCach, toa.maToaTau, toa.loaiToa
FROM ChuyenTau ct
JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau
JOIN Tau t ON ct.maTau = t.maTau
JOIN ToaTau toa ON t.maTau = toa.maTau;

OPEN chitietcho_cursor;

FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Determine giaCho based on loaiToa and khoangCach
    IF @loaiToaForCTC = 'ngoiMem'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 200000;
    END
    ELSE IF @loaiToaForCTC = 'giuongNam'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 300000;
    END
    ELSE
    BEGIN
        SET @giaChoForCTC = 0; -- Default or error value
    END

    -- Insert all seats for this train car and trip as 'conTrong' (available)
    INSERT INTO ChiTietCho (maCho, maChuyenTau, trangThaiCho, giaCho)
    SELECT c.maCho, @currentMaChuyenTau, 'conTrong', @giaChoForCTC
    FROM Cho c
    WHERE c.maToaTau = @maToaTauForCTC;

    FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;
END;

CLOSE chitietcho_cursor;
DEALLOCATE chitietcho_cursor;
GO

-- Sample HoaDon and Ve data (simplified for illustration)
-- This section demonstrates how to create a single transaction for one of the newly generated trips.

-- Find an available seat from the newly generated data
DECLARE @sampleMaChuyenTauNew VARCHAR(30);
DECLARE @sampleMaChoNew VARCHAR(20);
DECLARE @sampleGiaChoNew FLOAT;

SELECT TOP 1 @sampleMaChuyenTauNew = ctc.maChuyenTau, @sampleMaChoNew = ctc.maCho, @sampleGiaChoNew = ctc.giaCho
FROM ChiTietCho ctc
WHERE ctc.trangThaiCho = 'conTrong'
ORDER BY NEWID(); -- Pick a random available seat

IF @sampleMaChuyenTauNew IS NOT NULL
BEGIN
    DECLARE @maNhanVienBanVeSample VARCHAR(20) = '2023NV000001'; -- Assuming a 'banVe' employee
    DECLARE @maKhachHangThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhachHangThanThietSample = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' customer

    DECLARE @maKhuyenMaiThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiThanThietSample = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' promotion

    -- Generate a unique HoaDon ID
    DECLARE @maHoaDonNew VARCHAR(30) = @maNhanVienBanVeSample + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');

    -- Calculate thanhTien with a sample discount
    DECLARE @phanTramGiamGiaHD FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiThanThietSample);
    DECLARE @thanhTienHD FLOAT = @sampleGiaChoNew * (1 - @phanTramGiamGiaHD);
    DECLARE @tienKhachDuaHD FLOAT = @thanhTienHD + 50000; -- Assume customer pays slightly more
    DECLARE @tienTraLaiHD FLOAT = @tienKhachDuaHD - @thanhTienHD;

    INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
    (@maHoaDonNew, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD, @thanhTienHD, @tienKhachDuaHD, @tienTraLaiHD, @maKhachHangThanThietSample, @maKhuyenMaiThanThietSample, @maNhanVienBanVeSample);

    -- Get customer details for the ticket
    DECLARE @tenKhachHangVe NVARCHAR(50);
    DECLARE @cccd_HoChieuVe NVARCHAR(20);
    DECLARE @ngaySinhVe DATE;
    DECLARE @loaiVe NVARCHAR(50);

    SELECT @tenKhachHangVe = tenKhachHang, @cccd_HoChieuVe = cccd_HoChieu
    FROM KhachHang
    WHERE maKhachHang = @maKhachHangThanThietSample;

    -- For sample data, using a fixed birthday or employee's birthday for simplicity
    SET @ngaySinhVe = '1990-01-01';

    -- Determine loaiVe from ToaTau linked to maCho
    SELECT @loaiVe = tt.loaiToa
    FROM Cho c
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE c.maCho = @sampleMaChoNew;

    -- Generate a unique Ve ID: maCho + HHmmss + DDMMYY
    DECLARE @maVeNew VARCHAR(30) = @sampleMaChoNew + FORMAT(GETDATE(), 'HHmmss') + FORMAT(GETDATE(), 'ddMMyy');

    INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
    (@maVeNew, GETDATE(), 'hieuLuc', @tenKhachHangVe, @cccd_HoChieuVe, @ngaySinhVe, @loaiVe, @sampleGiaChoNew, 0.00, @maHoaDonNew, @sampleMaChoNew, @sampleMaChuyenTauNew);

    -- Update ChiTietCho status to 'daBan'
    UPDATE ChiTietCho
    SET trangThaiCho = 'daBan'
    WHERE maCho = @sampleMaChoNew AND maChuyenTau = @sampleMaChuyenTauNew;
END
GO

-- Recreate sequences (ensure they exist or are reset)
IF OBJECT_ID('SeqChuyenTau', 'SO') IS NOT NULL
    DROP SEQUENCE SeqChuyenTau;
GO
CREATE SEQUENCE SeqChuyenTau
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqHoaDon', 'SO') IS NOT NULL
    DROP SEQUENCE SeqHoaDon;
GO
CREATE SEQUENCE SeqHoaDon
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqKhachHang', 'SO') IS NOT NULL
    DROP SEQUENCE SeqKhachHang;
GO
CREATE SEQUENCE SeqKhachHang
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqGa', 'SO') IS NOT NULL
    DROP SEQUENCE SeqGa;
GO
CREATE SEQUENCE SeqGa
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTuyenTau', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTuyenTau;
GO
CREATE SEQUENCE SeqTuyenTau
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTauSE', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauSE;
GO
CREATE SEQUENCE SeqTauSE
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTauDP', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauDP;
GO
CREATE SEQUENCE SeqTauDP
    START WITH 1
    INCREMENT BY 1;
GO

IF OBJECT_ID('SeqTauDL', 'SO') IS NOT NULL
    DROP SEQUENCE SeqTauDL;
GO
CREATE SEQUENCE SeqTauDL
    START WITH 1
    INCREMENT BY 1;
GO

-- Insert base data (assuming these tables are empty or need re-populating)
-- NhanVien (from original script)
INSERT INTO NhanVien (maNhanVien, tenNhanVien, cccd_HoChieu, soDienThoai, ngaySinh, chucVu, gioiTinh, urlAnh, trangThaiNhanVien, email) VALUES
('2023NV000001', N'Phạm Trương Hoàng Phương', '123456789012', '0901234567', '2004-01-17', 'quanLy', 'nam', 'image/phuong.jpg', 'hoatDong', 'phuong@gmail.com'),
('2023NV000002', N'Phạm Viết Quân', '987654321098', '0912345678', '1995-08-22', 'quanLy', 'nam', 'image/quan.jpg', 'hoatDong', 'quan@gmail.com'),
('2024NV000001', N'Bùi Tấn Quang Trung', '456789123456', '0923456789', '1988-03-10', 'quanLy', 'nam', 'image/trung.jpg', 'hoatDong', 'trung@gmail.com'),
('2024NV000002', N'Trần Minh Tuấn', '789123456789', '0934567890', '1992-11-30', 'quanLy', 'nam', 'image/tuan.jpg', 'hoatDong', 'tuan@gmail.com');
GO

-- TaiKhoan (from original script)
INSERT INTO TaiKhoan (tenTaiKhoan, matKhau, maNhanVien) VALUES
('phuong123', 'matkhau1', '2023NV000001'),
('quan123', 'matkhau2', '2023NV000002'),
('trung123', 'matkhau3', '2024NV000001'),
('tuan123', 'matkhau4', '2024NV000002');
GO

-- KhachHang (as per your latest request for 20 customers, including one 'vangLai' generic)
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KH000001'), N'Nguyễn Thanh Hải', '079199212345', '0901112223', 'thanThiet', 'hoatDong', 'hai.nguyen@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000002'), N'Trần Bích Ngọc', '079199567890', '0912223334', 'thanThiet', 'hoatDong', 'ngoc.tran@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000003'), N'Lê Văn Đạt', '079198801234', '0863334445', 'thanThiet', 'hoatDong', 'dat.le@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000004'), N'Phạm Thị Mai Phương', '079200056789', '0384445556', 'thanThiet', 'hoatDong', 'maiphuong.pham@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000005'), N'Đỗ Quốc Anh', '079199098765', '0975556667', 'thanThiet', 'hoatDong', 'quocanh.do@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000006'), N'Hoàng Việt Đức', '079199312345', '0966667778', 'thanThiet', 'hoatDong', 'vietduc.hoang@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000007'), N'Vũ Thị Thùy Linh', '079199789012', '0937778889', 'thanThiet', 'hoatDong', 'thuylinh.vu@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000008'), N'Bùi Quang Trung', '079198754321', '0898889990', 'thanThiet', 'hoatDong', 'quangtrung.bui@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000009'), N'Dương Ánh Tuyết', '079200223456', '0709990001', 'thanThiet', 'hoatDong', 'tuyet.duong@email.com'),
(CONCAT(YEAR(GETDATE()), 'KH000010'), N'Nguyễn Thanh Duy', '079199834567', '0761112223', 'thanThiet', 'hoatDong', 'thanhduy.nguyen@email.com');
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KH000011'), N'Phan Ngọc Bích', '079198011223', '0982345678', 'vip', 'hoatDong', 'ngocbich.phan@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000012'), N'Trần Hữu Khang', '079197544556', '0913456789', 'vip', 'hoatDong', 'huukhang.tran@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000013'), N'Lý Thùy Dung', '079198377889', '0944567890', 'vip', 'hoatDong', 'thuydung.ly@vipmail.com'),
(CONCAT(YEAR(GETDATE()), 'KH000014'), N'Vũ Thanh Sang', '079197299001', '0885678901', 'vip', 'hoatDong', 'thanhsang.vu@vipmail.com');
INSERT INTO KhachHang (maKhachHang, tenKhachHang, cccd_HoChieu, soDienThoai, loaiKhachHang, trangThaiKhachHang, email) VALUES
(CONCAT(YEAR(GETDATE()), 'KHVL0001'), N'Khách Vãng Lai', NULL, NULL, 'vangLai', 'hoatDong', NULL);
GO

-- KhuyenMai (as per your request)
INSERT INTO KhuyenMai (maKhuyenMai, tenKhuyenMai, ngayBatDauSuKien, ngayKetThucSuKien, loaiKhachHang, phanTramGiamGiaSuKien) VALUES
(CONCAT(YEAR(GETDATE()), 'KM001'), N'Khuyến mãi hè sôi động', '2025-06-01', '2025-08-31', 'thanThiet', 0.10),
(CONCAT(YEAR(GETDATE()), 'KM002'), N'Ưu đãi VIP đặc biệt', '2025-05-20', '2025-12-31', 'vip', 0.15),
(CONCAT(YEAR(GETDATE()), 'KM003'), N'Giảm giá khách vãng lai', '2025-05-25', '2025-06-30', 'vangLai', 0.05),
(CONCAT(YEAR(GETDATE()), 'KM004'), N'Chào đón năm mới', '2025-01-01', '2025-01-31', 'thanThiet', 0.08),
(CONCAT(YEAR(GETDATE()), 'KM005'), N'Quốc Khánh 2/9', '2025-09-01', '2025-09-05', 'vip', 0.20),
(CONCAT(YEAR(GETDATE()), 'KM006'), N'Ngày phụ nữ Việt Nam', '2025-10-20', '2025-10-20', 'thanThiet', 0.12);
GO

-- Ga (as per your request)
DECLARE @sgMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @hnMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @dnMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @hueMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @ntMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @vinhMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));
DECLARE @dhMaGa VARCHAR(20) = CONCAT(YEAR(GETDATE()), 'GA', FORMAT(NEXT VALUE FOR SeqGa, '0000'));

INSERT INTO Ga (maGa, tenGa, diaChi) VALUES
(@sgMaGa, N'Ga Sài Gòn', N'Số 1 Nguyễn Thông, Phường 9, Quận 3, TP. Hồ Chí Minh'),
(@hnMaGa, N'Ga Hà Nội', N'120 Lê Duẩn, Cửa Nam, Hoàn Kiếm, Hà Nội'),
(@dnMaGa, N'Ga Đà Nẵng', N'791 Hải Phòng, Thanh Khê, Đà Nẵng'),
(@hueMaGa, N'Ga Huế', N'02 Bùi Thị Xuân, Phường Đúc, TP. Huế, Thừa Thiên Huế'),
(@ntMaGa, N'Ga Nha Trang', N'17 Thái Nguyên, Phước Tân, TP. Nha Trang, Khánh Hòa'),
(@vinhMaGa, N'Ga Vinh', N'1 Phan Bội Châu, Lê Lợi, TP. Vinh, Nghệ An'),
(@dhMaGa, N'Ga Đồng Hới', N'Tiểu khu 4, Nam Lý, TP. Đồng Hới, Quảng Bình');
GO

-- TuyenTau (as per your request)
-- Re-fetch maGa values after insertion
DECLARE @sgMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Sài Gòn');
DECLARE @hnMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Hà Nội');
DECLARE @dnMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Đà Nẵng');
DECLARE @hueMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Huế');
DECLARE @ntMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Nha Trang');
DECLARE @vinhMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Vinh');
DECLARE @dhMaGa_reget VARCHAR(20) = (SELECT maGa FROM Ga WHERE tenGa = N'Ga Đồng Hới');

INSERT INTO TuyenTau (maTuyenTau, tenTuyenTau, khoangCach, gaDi, gaDen) VALUES
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Hà Nội', 1726.0, @sgMaGa_reget, @hnMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Sài Gòn', 1726.0, @hnMaGa_reget, @sgMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Đà Nẵng', 961.0, @sgMaGa_reget, @dnMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đà Nẵng - Sài Gòn', 961.0, @dnMaGa_reget, @sgMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Huế', 688.0, @hnMaGa_reget, @hueMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Huế - Hà Nội', 688.0, @hueMaGa_reget, @hnMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Sài Gòn - Nha Trang', 411.0, @sgMaGa_reget, @ntMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Nha Trang - Sài Gòn', 411.0, @ntMaGa_reget, @sgMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Hà Nội - Vinh', 319.0, @hnMaGa_reget, @vinhMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Vinh - Hà Nội', 319.0, @vinhMaGa_reget, @hnMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đà Nẵng - Đồng Hới', 268.0, @dnMaGa_reget, @dhMaGa_reget),
(CONCAT(YEAR(GETDATE()), 'TT', FORMAT(NEXT VALUE FOR SeqTuyenTau, '0000')), N'Tuyến Đồng Hới - Đà Nẵng', 268.0, @dhMaGa_reget, @dnMaGa_reget);
GO

-- Tau (as per your request, 50 trains)
INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
(CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), N'SE01', 'hoatDong', 'tauChatLuong'),
(CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), N'SE02', 'hoatDong', 'tauChatLuong');
DECLARE @i_se INT = 3;
WHILE @i_se <= 30
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'SE', FORMAT(NEXT VALUE FOR SeqTauSE, '00')), CONCAT(N'SE', FORMAT(@i_se, '00')),
     CASE WHEN @i_se % 5 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END,
     'tauThongNhat');
    SET @i_se = @i_se + 1;
END;
DECLARE @i_dp INT = 1;
WHILE @i_dp <= 15
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'DP', FORMAT(NEXT VALUE FOR SeqTauDP, '00')), CONCAT(N'DP', FORMAT(@i_dp, '00')),
     CASE WHEN @i_dp % 4 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END,
     'tauDiaPhuong');
    SET @i_dp = @i_dp + 1;
END;
DECLARE @i_dl INT = 1;
WHILE @i_dl <= 10
BEGIN
    INSERT INTO Tau (maTau, tenTau, trangThaiTau, loaiTau) VALUES
    (CONCAT(YEAR(GETDATE()), 'DL', FORMAT(NEXT VALUE FOR SeqTauDL, '00')), CONCAT(N'DL', FORMAT(@i_dl, '00')),
     CASE WHEN @i_dl % 3 = 0 THEN 'khongHoatDong' ELSE 'hoatDong' END,
     'tauDuLich');
    SET @i_dl = @i_dl + 1;
END;
GO

-- Insert data for ToaTau (Train Cars) based on existing Tau data
DECLARE @currentMaTau VARCHAR(20);
DECLARE @currentLoaiTau NVARCHAR(50);
DECLARE @thuTuToa INT;

-- Cursor to iterate through each Tau (train)
DECLARE tau_cursor CURSOR FOR
SELECT maTau, loaiTau FROM Tau;

OPEN tau_cursor;

FETCH NEXT FROM tau_cursor INTO @currentMaTau, @currentLoaiTau;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @thuTuToa = 1;

    IF @currentLoaiTau = 'tauChatLuong' -- SE01, SE02
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 60);
    END
    ELSE IF @currentLoaiTau = 'tauThongNhat' -- SE03 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 8, 3, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 8, 3, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 70);
    END
    ELSE IF @currentLoaiTau = 'tauDiaPhuong' -- DP01 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 80);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 80);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 4, 2, 8, NULL);
    END
    ELSE IF @currentLoaiTau = 'tauDuLich' -- DL01 onwards
    BEGIN
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 50);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'ngoiMem', NULL, NULL, NULL, 50);
        SET @thuTuToa = @thuTuToa + 1;
        INSERT INTO ToaTau (maToaTau, thuTuToa, maTau, loaiToa, soHieuKhoang, soHieuTang, soLuongGiuong, soLuongGhe) VALUES
        (CONCAT(@currentMaTau, 'T', FORMAT(@thuTuToa, '00')), @thuTuToa, @currentMaTau, 'giuongNam', 6, 4, 24, NULL);
    END

    FETCH NEXT FROM tau_cursor INTO @currentMaTau, @currentLoaiTau;
END;

CLOSE tau_cursor;
DEALLOCATE tau_cursor;
GO

-- Insert data for Cho (Seats) - Based on ToaTau
-- Clear existing Cho data before regenerating
DELETE FROM Cho;
GO

DECLARE @maToaTauCho VARCHAR(20);
DECLARE @soLuongGheCho INT;
DECLARE @soLuongGiuongCho INT;
DECLARE @loaiToaCho NVARCHAR(20);
DECLARE @counterCho INT;
DECLARE @seatTypeAbbr VARCHAR(2);

DECLARE cho_cursor CURSOR FOR
SELECT maToaTau, loaiToa, soLuongGhe, soLuongGiuong
FROM ToaTau;

OPEN cho_cursor;

FETCH NEXT FROM cho_cursor INTO @maToaTauCho, @loaiToaCho, @soLuongGheCho, @soLuongGiuongCho;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @counterCho = 1;
    IF @loaiToaCho = 'ngoiMem' AND @soLuongGheCho IS NOT NULL
    BEGIN
        SET @seatTypeAbbr = 'GM'; -- Ghế Mềm
        WHILE @counterCho <= @soLuongGheCho
        BEGIN
            -- maCho format: [maToaTau][SeatTypeAbbr][SequentialSeatNumber]
            INSERT INTO Cho (maCho, soThuTuCho, maToaTau) VALUES
            (CONCAT(@maToaTauCho, @seatTypeAbbr, FORMAT(@counterCho, '00')), @counterCho, @maToaTauCho);
            SET @counterCho = @counterCho + 1;
        END
    END
    ELSE IF @loaiToaCho = 'giuongNam' AND @soLuongGiuongCho IS NOT NULL
    BEGIN
        SET @seatTypeAbbr = 'GN'; -- Giường Nằm
        WHILE @counterCho <= @soLuongGiuongCho
        BEGIN
            -- maCho format: [maToaTau][SeatTypeAbbr][SequentialSeatNumber]
            INSERT INTO Cho (maCho, soThuTuCho, maToaTau) VALUES
            (CONCAT(@maToaTauCho, @seatTypeAbbr, FORMAT(@counterCho, '00')), @counterCho, @maToaTauCho);
            SET @counterCho = @counterCho + 1;
        END
    END
    FETCH NEXT FROM cho_cursor INTO @maToaTauCho, @loaiToaCho, @soLuongGheCho, @soLuongGiuongCho;
END;

CLOSE cho_cursor;
DEALLOCATE cho_cursor;
GO


-- Declare variables for date range
DECLARE @startDate DATE = GETDATE(); -- Ngày bắt đầu: hôm nay
DECLARE @endDate DATE = DATEADD(week, 1, @startDate); -- Ngày kết thúc: 1 tuần sau
DECLARE @currentDate DATE;
DECLARE @numTripsForDay INT;
DECLARE @tripIndex INT;
DECLARE @gioKhoiHanh TIME;

-- Temporary table to hold active trains and their types
DECLARE @activeTrains TABLE (maTau VARCHAR(20), loaiTau NVARCHAR(50));
INSERT INTO @activeTrains SELECT maTau, loaiTau FROM Tau WHERE trangThaiTau = 'hoatDong';

-- Cursor to iterate through each TuyenTau
DECLARE tuyentau_cursor CURSOR FOR
SELECT maTuyenTau FROM TuyenTau;

DECLARE @maTuyenTauLoop VARCHAR(20);

OPEN tuyentau_cursor;

FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;

WHILE @@FETCH_STATUS = 0
BEGIN
    SET @currentDate = @startDate;
    WHILE @currentDate <= @endDate
    BEGIN
        -- Reset available trains for the current day
        -- Note: @dailyAvailableTrains needs to be declared and populated within this scope or globally
        -- For simplicity, re-inserting into a table variable.
        DELETE FROM @activeTrains; -- Clear for new day
        INSERT INTO @activeTrains SELECT maTau, loaiTau FROM Tau WHERE trangThaiTau = 'hoatDong';


        SET @numTripsForDay = CAST(RAND() * 3 + 4 AS INT); -- Randomly 4 to 6 trips per day for each route
        SET @tripIndex = 1;

        WHILE @tripIndex <= @numTripsForDay
        BEGIN
            DECLARE @selectedTau VARCHAR(20);
            DECLARE @selectedLoaiTau NVARCHAR(50);

            -- Select a random available train for this trip on this day
            SELECT TOP 1 @selectedTau = maTau, @selectedLoaiTau = loaiTau
            FROM @activeTrains
            ORDER BY NEWID();

            IF @selectedTau IS NOT NULL
            BEGIN
                -- Remove the selected train from the daily available list
                DELETE FROM @activeTrains WHERE maTau = @selectedTau;

                -- Generate departure time (staggered for realism)
                SET @gioKhoiHanh = DATEADD(hour, (6 + (@tripIndex - 1) * 3) % 24, '00:00:00'); -- Example: 6:00, 9:00, 12:00, 15:00, 18:00, 21:00
                SET @gioKhoiHanh = DATEADD(minute, CAST(RAND() * 59 AS INT), @gioKhoiHanh); -- Add random minutes

                -- Determine loaiTau abbreviation for maChuyenTau
                DECLARE @loaiTauAbbr VARCHAR(2);
                IF @selectedLoaiTau = 'tauChatLuong' OR @selectedLoaiTau = 'tauThongNhat' SET @loaiTauAbbr = 'SE';
                ELSE IF @selectedLoaiTau = 'tauDiaPhuong' SET @loaiTauAbbr = 'DP';
                ELSE IF @selectedLoaiTau = 'tauDuLich' SET @loaiTauAbbr = 'DL';
                ELSE SET @loaiTauAbbr = 'XX'; -- Fallback

                -- Construct maChuyenTau:
                DECLARE @maChuyenTauNew VARCHAR(30) = CONCAT(YEAR(@currentDate), @loaiTauAbbr, FORMAT(NEXT VALUE FOR SeqChuyenTau, '000000'));

                -- Calculate estimated arrival date and time
                DECLARE @tripDurationHours INT = 12 + CAST(RAND() * 12 AS INT); -- Duration 12-24 hours
                DECLARE @arrivalDateTime DATETIME = DATEADD(hour, @tripDurationHours, CAST(@currentDate AS DATETIME) + CAST(@gioKhoiHanh AS DATETIME));
                
                DECLARE @ngayDuKien DATE = CAST(@arrivalDateTime AS DATE);
                DECLARE @gioDuKien TIME = CAST(@arrivalDateTime AS TIME);

                INSERT INTO ChuyenTau (maChuyenTau, ngayKhoiHanh, gioKhoiHanh, ngayDuKien, gioDuKien, trangThaiChuyenTau, maTau, maTuyenTau)
                VALUES (
                    @maChuyenTauNew,
                    @currentDate,
                    @gioKhoiHanh,
                    @ngayDuKien,
                    @gioDuKien,
                    'hoatDong',
                    @selectedTau,
                    @maTuyenTauLoop
                );
            END
            ELSE
            BEGIN
                -- Not enough unique trains for this day/route, break from inner loop
                BREAK;
            END
            SET @tripIndex = @tripIndex + 1;
        END
        SET @currentDate = DATEADD(day, 1, @currentDate);
    END
    FETCH NEXT FROM tuyentau_cursor INTO @maTuyenTauLoop;
END;

CLOSE tuyentau_cursor;
DEALLOCATE tuyentau_cursor;
GO

-- Regenerate ChiTietCho (Seat Details) for newly generated ChuyenTau
DECLARE @currentMaChuyenTau VARCHAR(30);
DECLARE @currentMaTauForCTC VARCHAR(20);
DECLARE @khoangCachForCTC FLOAT;
DECLARE @maToaTauForCTC VARCHAR(20);
DECLARE @giaChoForCTC FLOAT;
DECLARE @loaiToaForCTC NVARCHAR(20);

DECLARE chitietcho_cursor CURSOR FOR
SELECT ct.maChuyenTau, ct.maTau, tt.khoangCach, toa.maToaTau, toa.loaiToa
FROM ChuyenTau ct
JOIN TuyenTau tt ON ct.maTuyenTau = tt.maTuyenTau
JOIN Tau t ON ct.maTau = t.maTau
JOIN ToaTau toa ON t.maTau = toa.maTau;

OPEN chitietcho_cursor;

FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;

WHILE @@FETCH_STATUS = 0
BEGIN
    -- Determine giaCho based on loaiToa and khoangCach
    IF @loaiToaForCTC = 'ngoiMem'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 200000;
    END
    ELSE IF @loaiToaForCTC = 'giuongNam'
    BEGIN
        SET @giaChoForCTC = (@khoangCachForCTC / 100.0) * 300000;
    END
    ELSE
    BEGIN
        SET @giaChoForCTC = 0; -- Default or error value
    END

    -- Insert all seats for this train car and trip as 'conTrong' (available)
    INSERT INTO ChiTietCho (maCho, maChuyenTau, trangThaiCho, giaCho)
    SELECT c.maCho, @currentMaChuyenTau, 'conTrong', @giaChoForCTC
    FROM Cho c
    WHERE c.maToaTau = @maToaTauForCTC;

    FETCH NEXT FROM chitietcho_cursor INTO @currentMaChuyenTau, @currentMaTauForCTC, @khoangCachForCTC, @maToaTauForCTC, @loaiToaForCTC;
END;

CLOSE chitietcho_cursor;
DEALLOCATE chitietcho_cursor;
GO

-- Sample HoaDon and Ve data (simplified for illustration)
-- This section demonstrates how to create a single transaction for one of the newly generated trips.

-- Find an available seat from the newly generated data
DECLARE @sampleMaChuyenTauNew VARCHAR(30);
DECLARE @sampleMaChoNew VARCHAR(20);
DECLARE @sampleGiaChoNew FLOAT;

SELECT TOP 1 @sampleMaChuyenTauNew = ctc.maChuyenTau, @sampleMaChoNew = ctc.maCho, @sampleGiaChoNew = ctc.giaCho
FROM ChiTietCho ctc
WHERE ctc.trangThaiCho = 'conTrong'
ORDER BY NEWID(); -- Pick a random available seat

IF @sampleMaChuyenTauNew IS NOT NULL
BEGIN
    DECLARE @maNhanVienBanVeSample VARCHAR(20) = '2023NV000001'; -- Assuming a 'banVe' employee
    DECLARE @maKhachHangThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhachHangThanThietSample = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' customer

    DECLARE @maKhuyenMaiThanThietSample VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiThanThietSample = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID(); -- Pick a random 'thanThiet' promotion

    -- Generate a unique HoaDon ID
    DECLARE @maHoaDonNew VARCHAR(30) = @maNhanVienBanVeSample + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');

    -- Calculate thanhTien with a sample discount
    DECLARE @phanTramGiamGiaHD FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiThanThietSample);
    DECLARE @thanhTienHD FLOAT = @sampleGiaChoNew * (1 - @phanTramGiamGiaHD);
    DECLARE @tienKhachDuaHD FLOAT = @thanhTienHD + 50000; -- Assume customer pays slightly more
    DECLARE @tienTraLaiHD FLOAT = @tienKhachDuaHD - @thanhTienHD;

    INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
    (@maHoaDonNew, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD, @thanhTienHD, @tienKhachDuaHD, @tienTraLaiHD, @maKhachHangThanThietSample, @maKhuyenMaiThanThietSample, @maNhanVienBanVeSample);

    -- Get customer details for the ticket
    DECLARE @tenKhachHangVe NVARCHAR(50);
    DECLARE @cccd_HoChieuVe NVARCHAR(20);
    DECLARE @ngaySinhVe DATE;
    DECLARE @loaiVe NVARCHAR(50);

    SELECT @tenKhachHangVe = tenKhachHang, @cccd_HoChieuVe = cccd_HoChieu
    FROM KhachHang
    WHERE maKhachHang = @maKhachHangThanThietSample;

    -- For sample data, using a fixed birthday or employee's birthday for simplicity
    SET @ngaySinhVe = '1990-01-01';

    -- Determine loaiVe from ToaTau linked to maCho
    SELECT @loaiVe = tt.loaiToa
    FROM Cho c
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE c.maCho = @sampleMaChoNew;

    -- Generate a unique Ve ID: maCho + HHmmss + DDMMYY
    DECLARE @maVeNew VARCHAR(30) = @sampleMaChoNew + FORMAT(GETDATE(), 'HHmmss') + FORMAT(GETDATE(), 'ddMMyy');

    INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
    (@maVeNew, GETDATE(), 'hieuLuc', @tenKhachHangVe, @cccd_HoChieuVe, @ngaySinhVe, @loaiVe, @sampleGiaChoNew, 0.00, @maHoaDonNew, @sampleMaChoNew, @sampleMaChuyenTauNew);

    -- Update ChiTietCho status to 'daBan'
    UPDATE ChiTietCho
    SET trangThaiCho = 'daBan'
    WHERE maCho = @sampleMaChoNew AND maChuyenTau = @sampleMaChuyenTauNew;
END
GO

-- Create a sequence for HoaDon IDs if it doesn't exist
IF OBJECT_ID('SeqHoaDon', 'SO') IS NOT NULL
    DROP SEQUENCE SeqHoaDon;
GO
CREATE SEQUENCE SeqHoaDon
    START WITH 1
    INCREMENT BY 1;
GO

-- Create a sequence for Ve IDs if it doesn't exist
IF OBJECT_ID('SeqVe', 'SO') IS NOT NULL
    DROP SEQUENCE SeqVe;
GO
CREATE SEQUENCE SeqVe
    START WITH 1
    INCREMENT BY 1;
GO

-- Insert sample data for HoaDon and Ve
-- This script will simulate a few transactions (HoaDon) and corresponding tickets (Ve)

-- Transaction 1: A ticket sold to a 'thanThiet' customer
BEGIN TRY
    DECLARE @maNhanVienBanVe1 VARCHAR(20) = (SELECT TOP 1 maNhanVien FROM NhanVien WHERE chucVu = 'quanLy' ORDER BY NEWID()); -- Pick a random 'banVe' employee
    DECLARE @maKhachHangThanThiet1 VARCHAR(20);
    SELECT TOP 1 @maKhachHangThanThiet1 = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();
    DECLARE @maKhuyenMaiThanThiet1 VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiThanThiet1 = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();

    -- Find an available seat for a random trip
    DECLARE @sampleMaChuyenTau1 VARCHAR(30);
    DECLARE @sampleMaCho1 VARCHAR(20);
    DECLARE @sampleGiaCho1 FLOAT;
    DECLARE @loaiToa1 NVARCHAR(50);

    SELECT TOP 1 @sampleMaChuyenTau1 = ctc.maChuyenTau, @sampleMaCho1 = ctc.maCho, @sampleGiaCho1 = ctc.giaCho, @loaiToa1 = tt.loaiToa
    FROM ChiTietCho ctc
    JOIN Cho c ON ctc.maCho = c.maCho
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'conTrong'
    ORDER BY NEWID();

    IF @sampleMaChuyenTau1 IS NOT NULL
    BEGIN
        -- Generate HoaDon ID: maNhanVien + HD + ngayTaoHoaDon (YYYYMMDD) + XXXX
        DECLARE @maHoaDon1 VARCHAR(30) = @maNhanVienBanVe1 + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');
        DECLARE @phanTramGiamGiaHD1 FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiThanThiet1);
        DECLARE @thanhTienHD1 FLOAT = @sampleGiaCho1 * (1 - @phanTramGiamGiaHD1);
        DECLARE @tienKhachDuaHD1 FLOAT = @thanhTienHD1 + 100000; -- Customer pays more
        DECLARE @tienTraLaiHD1 FLOAT = @tienKhachDuaHD1 - @thanhTienHD1;

        INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
        (@maHoaDon1, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD1, @thanhTienHD1, @tienKhachDuaHD1, @tienTraLaiHD1, @maKhachHangThanThiet1, @maKhuyenMaiThanThiet1, @maNhanVienBanVe1);

        -- Get customer details for the ticket
        DECLARE @tenKhachHangVe1 NVARCHAR(100);
        DECLARE @cccd_HoChieuVe1 NVARCHAR(20);
        DECLARE @ngaySinhVe1 DATE;
        SELECT @tenKhachHangVe1 = tenKhachHang, @cccd_HoChieuVe1 = cccd_HoChieu FROM KhachHang WHERE maKhachHang = @maKhachHangThanThiet1;
        SET @ngaySinhVe1 = DATEADD(year, -CAST(RAND() * 30 + 20 AS INT), GETDATE()); -- Random birthday between 20-50 years old

        -- Generate Ve ID: maChuyenTau + namTaoVe + maCho + XXX
        DECLARE @maVe1 VARCHAR(30) = @sampleMaChuyenTau1 + FORMAT(YEAR(GETDATE()), '0000') + @sampleMaCho1 + FORMAT(NEXT VALUE FOR SeqVe, '000');

        INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
        (@maVe1, GETDATE(), 'hieuLuc', @tenKhachHangVe1, @cccd_HoChieuVe1, @ngaySinhVe1, @loaiToa1, @sampleGiaCho1, @phanTramGiamGiaHD1, @maHoaDon1, @sampleMaCho1, @sampleMaChuyenTau1);

        -- Update ChiTietCho status to 'daBan'
        UPDATE ChiTietCho SET trangThaiCho = 'daBan' WHERE maCho = @sampleMaCho1 AND maChuyenTau = @sampleMaChuyenTau1;
    END
END TRY
BEGIN CATCH
    PRINT 'Error inserting Transaction 1: ' + ERROR_MESSAGE();
END CATCH;
GO

-- Transaction 2: Another ticket sold to a 'vip' customer
BEGIN TRY
    DECLARE @maNhanVienBanVe2 VARCHAR(20) = (SELECT TOP 1 maNhanVien FROM NhanVien WHERE chucVu = 'quanLy' ORDER BY NEWID());
    DECLARE @maKhachHangVip2 VARCHAR(20);
    SELECT TOP 1 @maKhachHangVip2 = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'vip' ORDER BY NEWID();
    DECLARE @maKhuyenMaiVip2 VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiVip2 = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'vip' ORDER BY NEWID();

    DECLARE @sampleMaChuyenTau2 VARCHAR(30);
    DECLARE @sampleMaCho2 VARCHAR(20);
    DECLARE @sampleGiaCho2 FLOAT;
    DECLARE @loaiToa2 NVARCHAR(50);

    SELECT TOP 1 @sampleMaChuyenTau2 = ctc.maChuyenTau, @sampleMaCho2 = ctc.maCho, @sampleGiaCho2 = ctc.giaCho, @loaiToa2 = tt.loaiToa
    FROM ChiTietCho ctc
    JOIN Cho c ON ctc.maCho = c.maCho
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'conTrong' AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau1, '') -- Ensure different trip than previous
    ORDER BY NEWID();

    IF @sampleMaChuyenTau2 IS NOT NULL
    BEGIN
        DECLARE @maHoaDon2 VARCHAR(30) = @maNhanVienBanVe2 + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');
        DECLARE @phanTramGiamGiaHD2 FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiVip2);
        DECLARE @thanhTienHD2 FLOAT = @sampleGiaCho2 * (1 - @phanTramGiamGiaHD2);
        DECLARE @tienKhachDuaHD2 FLOAT = @thanhTienHD2 + 50000;
        DECLARE @tienTraLaiHD2 FLOAT = @tienKhachDuaHD2 - @thanhTienHD2;

        INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
        (@maHoaDon2, GETDATE(), GETDATE(), 'chuyenKhoan', @phanTramGiamGiaHD2, @thanhTienHD2, @tienKhachDuaHD2, @tienTraLaiHD2, @maKhachHangVip2, @maKhuyenMaiVip2, @maNhanVienBanVe2);

        DECLARE @tenKhachHangVe2 NVARCHAR(100);
        DECLARE @cccd_HoChieuVe2 NVARCHAR(20);
        DECLARE @ngaySinhVe2 DATE;
        SELECT @tenKhachHangVe2 = tenKhachHang, @cccd_HoChieuVe2 = cccd_HoChieu FROM KhachHang WHERE maKhachHang = @maKhachHangVip2;
        SET @ngaySinhVe2 = DATEADD(year, -CAST(RAND() * 40 + 18 AS INT), GETDATE());

        DECLARE @maVe2 VARCHAR(30) = @sampleMaChuyenTau2 + FORMAT(YEAR(GETDATE()), '0000') + @sampleMaCho2 + FORMAT(NEXT VALUE FOR SeqVe, '000');

        INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
        (@maVe2, GETDATE(), 'hieuLuc', @tenKhachHangVe2, @cccd_HoChieuVe2, @ngaySinhVe2, @loaiToa2, @sampleGiaCho2, @phanTramGiamGiaHD2, @maHoaDon2, @sampleMaCho2, @sampleMaChuyenTau2);

        UPDATE ChiTietCho SET trangThaiCho = 'daBan' WHERE maCho = @sampleMaCho2 AND maChuyenTau = @sampleMaChuyenTau2;
    END
END TRY
BEGIN CATCH
    PRINT 'Error inserting Transaction 2: ' + ERROR_MESSAGE();
END CATCH;
GO

-- Transaction 3: A ticket sold to a 'vangLai' customer (no specific promotion)
BEGIN TRY
    DECLARE @maNhanVienBanVe3 VARCHAR(20) = (SELECT TOP 1 maNhanVien FROM NhanVien WHERE chucVu = 'quanLy' ORDER BY NEWID());
    DECLARE @maKhachHangVangLai3 VARCHAR(20);
    SELECT TOP 1 @maKhachHangVangLai3 = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'vangLai' ORDER BY NEWID();
    -- No specific promotion for 'vangLai' in this case, or use a general one

    DECLARE @sampleMaChuyenTau3 VARCHAR(30);
    DECLARE @sampleMaCho3 VARCHAR(20);
    DECLARE @sampleGiaCho3 FLOAT;
    DECLARE @loaiToa3 NVARCHAR(50);

    SELECT TOP 1 @sampleMaChuyenTau3 = ctc.maChuyenTau, @sampleMaCho3 = ctc.maCho, @sampleGiaCho3 = ctc.giaCho, @loaiToa3 = tt.loaiToa
    FROM ChiTietCho ctc
    JOIN Cho c ON ctc.maCho = c.maCho
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'conTrong' AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau1, '') AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau2, '')
    ORDER BY NEWID();

    IF @sampleMaChuyenTau3 IS NOT NULL
    BEGIN
        DECLARE @maHoaDon3 VARCHAR(30) = @maNhanVienBanVe3 + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');
        DECLARE @phanTramGiamGiaHD3 FLOAT = 0.00; -- No discount for vangLai in this example
        DECLARE @thanhTienHD3 FLOAT = @sampleGiaCho3 * (1 - @phanTramGiamGiaHD3);
        DECLARE @tienKhachDuaHD3 FLOAT = @thanhTienHD3 + 20000;
        DECLARE @tienTraLaiHD3 FLOAT = @tienKhachDuaHD3 - @thanhTienHD3;

        INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
        (@maHoaDon3, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD3, @thanhTienHD3, @tienKhachDuaHD3, @tienTraLaiHD3, @maKhachHangVangLai3, NULL, @maNhanVienBanVe3); -- No KhuyenMai for vangLai

        DECLARE @tenKhachHangVe3 NVARCHAR(100);
        DECLARE @cccd_HoChieuVe3 NVARCHAR(20);
        DECLARE @ngaySinhVe3 DATE;
        SELECT @tenKhachHangVe3 = tenKhachHang, @cccd_HoChieuVe3 = cccd_HoChieu FROM KhachHang WHERE maKhachHang = @maKhachHangVangLai3;
        SET @ngaySinhVe3 = '1990-01-01'; -- Placeholder for vangLai

        DECLARE @maVe3 VARCHAR(30) = @sampleMaChuyenTau3 + FORMAT(YEAR(GETDATE()), '0000') + @sampleMaCho3 + FORMAT(NEXT VALUE FOR SeqVe, '000');

        INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
        (@maVe3, GETDATE(), 'hieuLuc', @tenKhachHangVe3, @cccd_HoChieuVe3, @ngaySinhVe3, @loaiToa3, @sampleGiaCho3, @phanTramGiamGiaHD3, @maHoaDon3, @sampleMaCho3, @sampleMaChuyenTau3);

        UPDATE ChiTietCho SET trangThaiCho = 'daBan' WHERE maCho = @sampleMaCho3 AND maChuyenTau = @sampleMaChuyenTau3;
    END
END TRY
BEGIN CATCH
    PRINT 'Error inserting Transaction 3: ' + ERROR_MESSAGE();
END CATCH;
GO

-- Transaction 4: A ticket sold to a 'thanThiet' customer with a future date for the ticket (but current date for HoaDon)
BEGIN TRY
    DECLARE @maNhanVienBanVe4 VARCHAR(20) = (SELECT TOP 1 maNhanVien FROM NhanVien WHERE chucVu = 'quanLy' ORDER BY NEWID());
    DECLARE @maKhachHangThanThiet4 VARCHAR(20);
    SELECT TOP 1 @maKhachHangThanThiet4 = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();
    DECLARE @maKhuyenMaiThanThiet4 VARCHAR(20);
    SELECT TOP 1 @maKhuyenMaiThanThiet4 = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();

    DECLARE @sampleMaChuyenTau4 VARCHAR(30);
    DECLARE @sampleMaCho4 VARCHAR(20);
    DECLARE @sampleGiaCho4 FLOAT;
    DECLARE @loaiToa4 NVARCHAR(50);

    -- Find an available seat for a trip in the future (e.g., tomorrow)
    SELECT TOP 1 @sampleMaChuyenTau4 = ctc.maChuyenTau, @sampleMaCho4 = ctc.maCho, @sampleGiaCho4 = ctc.giaCho, @loaiToa4 = tt.loaiToa
    FROM ChiTietCho ctc
    JOIN ChuyenTau ct ON ctc.maChuyenTau = ct.maChuyenTau
    JOIN Cho c ON ctc.maCho = c.maCho
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'conTrong'
      AND ct.ngayKhoiHanh > GETDATE() -- Ensure future trip
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau1, '')
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau2, '')
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau3, '')
    ORDER BY NEWID();

    IF @sampleMaChuyenTau4 IS NOT NULL
    BEGIN
        DECLARE @maHoaDon4 VARCHAR(30) = @maNhanVienBanVe4 + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');
        DECLARE @phanTramGiamGiaHD4 FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMaiThanThiet4);
        DECLARE @thanhTienHD4 FLOAT = @sampleGiaCho4 * (1 - @phanTramGiamGiaHD4);
        DECLARE @tienKhachDuaHD4 FLOAT = @thanhTienHD4 + 75000;
        DECLARE @tienTraLaiHD4 FLOAT = @tienKhachDuaHD4 - @thanhTienHD4;

        INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
        (@maHoaDon4, GETDATE(), GETDATE(), 'chuyenKhoan', @phanTramGiamGiaHD4, @thanhTienHD4, @tienKhachDuaHD4, @tienTraLaiHD4, @maKhachHangThanThiet4, @maKhuyenMaiThanThiet4, @maNhanVienBanVe4);

        DECLARE @tenKhachHangVe4 NVARCHAR(100);
        DECLARE @cccd_HoChieuVe4 NVARCHAR(20);
        DECLARE @ngaySinhVe4 DATE;
        SELECT @tenKhachHangVe4 = tenKhachHang, @cccd_HoChieuVe4 = cccd_HoChieu FROM KhachHang WHERE maKhachHang = @maKhachHangThanThiet4;
        SET @ngaySinhVe4 = DATEADD(year, -CAST(RAND() * 25 + 22 AS INT), GETDATE());

        DECLARE @maVe4 VARCHAR(30) = @sampleMaChuyenTau4 + FORMAT(YEAR(GETDATE()), '0000') + @sampleMaCho4 + FORMAT(NEXT VALUE FOR SeqVe, '000');

        INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
        (@maVe4, GETDATE(), 'hieuLuc', @tenKhachHangVe4, @cccd_HoChieuVe4, @ngaySinhVe4, @loaiToa4, @sampleGiaCho4, @phanTramGiamGiaHD4, @maHoaDon4, @sampleMaCho4, @sampleMaChuyenTau4);

        UPDATE ChiTietCho SET trangThaiCho = 'daBan' WHERE maCho = @sampleMaCho4 AND maChuyenTau = @sampleMaChuyenTau4;
    END
END TRY
BEGIN CATCH
    PRINT 'Error inserting Transaction 4: ' + ERROR_MESSAGE();
END CATCH;
GO

-- Transaction 5: A ticket that is "cancelled" (daHuy)
BEGIN TRY
    DECLARE @maNhanVienBanVe5 VARCHAR(20) = (SELECT TOP 1 maNhanVien FROM NhanVien WHERE chucVu = 'quanLy' ORDER BY NEWID());
    DECLARE @maKhachHang5 VARCHAR(20);
    SELECT TOP 1 @maKhachHang5 = maKhachHang FROM KhachHang WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();
    DECLARE @maKhuyenMai5 VARCHAR(20);
    SELECT TOP 1 @maKhuyenMai5 = maKhuyenMai FROM KhuyenMai WHERE loaiKhachHang = 'thanThiet' ORDER BY NEWID();

    DECLARE @sampleMaChuyenTau5 VARCHAR(30);
    DECLARE @sampleMaCho5 VARCHAR(20);
    DECLARE @sampleGiaCho5 FLOAT;
    DECLARE @loaiToa5 NVARCHAR(50);

    SELECT TOP 1 @sampleMaChuyenTau5 = ctc.maChuyenTau, @sampleMaCho5 = ctc.maCho, @sampleGiaCho5 = ctc.giaCho, @loaiToa5 = tt.loaiToa
    FROM ChiTietCho ctc
    JOIN ChuyenTau ct ON ctc.maChuyenTau = ct.maChuyenTau
    JOIN Cho c ON ctc.maCho = c.maCho
    JOIN ToaTau tt ON c.maToaTau = tt.maToaTau
    WHERE ctc.trangThaiCho = 'conTrong'
      AND ct.ngayKhoiHanh > GETDATE()
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau1, '')
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau2, '')
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau3, '')
      AND ctc.maChuyenTau <> ISNULL(@sampleMaChuyenTau4, '')
    ORDER BY NEWID();

    IF @sampleMaChuyenTau5 IS NOT NULL
    BEGIN
        DECLARE @maHoaDon5 VARCHAR(30) = @maNhanVienBanVe5 + 'HD' + FORMAT(GETDATE(), 'yyyyMMdd') + FORMAT(NEXT VALUE FOR SeqHoaDon, '0000');
        DECLARE @phanTramGiamGiaHD5 FLOAT = (SELECT phanTramGiamGiaSuKien FROM KhuyenMai WHERE maKhuyenMai = @maKhuyenMai5);
        DECLARE @thanhTienHD5 FLOAT = @sampleGiaCho5 * (1 - @phanTramGiamGiaHD5);
        DECLARE @tienKhachDuaHD5 FLOAT = @thanhTienHD5;
        DECLARE @tienTraLaiHD5 FLOAT = 0.00;

        INSERT INTO HoaDon (maHoaDon, ngayTaoHoaDon, gioTaoHoaDon, phuongThucThanhToan, phanTramGiamGia, thanhTien, tienKhachDua, tienTraLai, maKhachHang, maKhuyenMai, maNhanVien) VALUES
        (@maHoaDon5, GETDATE(), GETDATE(), 'tienMat', @phanTramGiamGiaHD5, @thanhTienHD5, @tienKhachDuaHD5, @tienTraLaiHD5, @maKhachHang5, @maKhuyenMai5, @maNhanVienBanVe5);

        DECLARE @tenKhachHangVe5 NVARCHAR(100);
        DECLARE @cccd_HoChieuVe5 NVARCHAR(20);
        DECLARE @ngaySinhVe5 DATE;
        SELECT @tenKhachHangVe5 = tenKhachHang, @cccd_HoChieuVe5 = cccd_HoChieu FROM KhachHang WHERE maKhachHang = @maKhachHang5;
        SET @ngaySinhVe5 = DATEADD(year, -CAST(RAND() * 35 + 20 AS INT), GETDATE());

        DECLARE @maVe5 VARCHAR(30) = @sampleMaChuyenTau5 + FORMAT(YEAR(GETDATE()), '0000') + @sampleMaCho5 + FORMAT(NEXT VALUE FOR SeqVe, '000');

        INSERT INTO Ve (maVe, ngayTaoVe, trangThaiVe, tenKhachHang, cccd_HoChieu, ngaySinh, loaiVe, giaVe, phanTramGiamGiaCoDinh, maHoaDon, maCho, maChuyenTau) VALUES
        (@maVe5, GETDATE(), 'daHuy', @tenKhachHangVe5, @cccd_HoChieuVe5, @ngaySinhVe5, @loaiToa5, @sampleGiaCho5, @phanTramGiamGiaHD5, @maHoaDon5, @sampleMaCho5, @sampleMaChuyenTau5);

        -- When a ticket is cancelled, the seat should become 'conTrong' again
        UPDATE ChiTietCho SET trangThaiCho = 'conTrong' WHERE maCho = @sampleMaCho5 AND maChuyenTau = @sampleMaChuyenTau5;
    END
END TRY
BEGIN CATCH
    PRINT 'Error inserting Transaction 5: ' + ERROR_MESSAGE();
END CATCH;
GO