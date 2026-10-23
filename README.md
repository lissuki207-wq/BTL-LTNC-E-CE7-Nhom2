# Auctiva — Hệ thống đấu giá trực tuyến

Dự án bài tập lớn **Lập trình nâng cao 2026**: ứng dụng desktop JavaFX, server Java Socket/JSON và cơ sở dữ liệu H2 lưu trên đĩa. Bản này tích hợp cả giao diện, server, nghiệp vụ và lưu trữ từ bộ Model theo sơ đồ đã cung cấp.

![Giao diện Auctiva](docs/images/03-kham-pha.png)

## Chạy nhanh trên Windows

1. **Giải nén toàn bộ ZIP** ra thư mục, ví dụ `D:\BTL\Auctiva`. Không chạy file ngay trong cửa sổ xem ZIP.
2. Cần **JDK 17 hoặc 21**. Nếu đang dùng IntelliJ, vào **File → Project Structure → Project → SDK → Download JDK**, chọn 17 hoặc 21. Script nhận cả JDK trong `%USERPROFILE%\.jdks`.
3. Mở **`CHAY_TAT_CA.bat`**. Lần đầu cần Internet để tải Maven và các thư viện; chờ dòng `BUILD SUCCESS`.
4. Một cửa sổ **AUCTIVA SERVER** được mở và giữ chạy. Sau đó **cửa sổ giao diện Auctiva** xuất hiện.
5. Đăng nhập **`minh` / `Demo@123`**, hoặc bấm **Người mua** rồi **Đăng nhập**.

**Đây là ứng dụng desktop, không mở bằng trình duyệt.** `ServerMain` chỉ hiện console. `ClientLauncher` mới mở cửa sổ giao diện. Thư mục `.tools`, `target`, `data` được tạo tự động khi cần.

Có thể mở **`BAT_DAU.html`** để đọc hướng dẫn có hình. Hướng dẫn chi tiết: [docs/CHAY_VA_SU_DUNG.md](docs/CHAY_VA_SU_DUNG.md).

| File | Công dụng |
|---|---|
| `CHAY_TAT_CA.bat` | Build, mở server nếu chưa có, rồi mở một client |
| `1_CHAY_SERVER.bat` | Chỉ mở server, giữ cửa sổ này chạy |
| `2_MO_UNG_DUNG.bat` | Mở thêm một cửa sổ ứng dụng; dùng để demo nhiều người |
| `3_KIEM_THU.bat` | Chạy bộ kiểm thử nghiệp vụ, mạng và kiểm tra định dạng |

Script chỉ thiết lập Java cho tiến trình đang chạy, không sửa biến môi trường toàn hệ thống. Maven tải từ Maven Central và kiểm tra SHA-512. Lần đầu tải có thể mất vài phút; các lần sau dùng bộ nhớ đệm.

## Tài khoản mẫu

Server do các script trên khởi động có `--demo`.

| Tên đăng nhập | Mật khẩu | Vai trò |
|---|---|---|
| `minh` | `Demo@123` | Người mua |
| `linh` | `Demo@123` | Người mua thứ hai để demo realtime |
| `seller` | `Demo@123` | Người bán |
| `admin` | `Demo@123` | Quản trị |

Tài khoản mẫu được cấp 50.000.000 đồng **mô phỏng**; số dư khả dụng có thể thấp hơn do đang giữ tiền cho phiên mẫu. Đăng ký mới bắt đầu từ 0, vào **Xem ví & nạp tiền** để nạp mô phỏng. Có thể chọn người mua, người bán hoặc cả hai. Không cho đăng ký admin qua giao diện.

Dữ liệu mẫu có 6 phiên đang chạy và 1 phiên sắp mở. Một phiên ngắn 2 phút, tắt gia hạn, dành để demo kết thúc. Server không xóa dữ liệu cũ; khi chạy `--demo` và không còn phiên hoạt động, nó tạo thêm đợt sản phẩm mẫu mới.

## Mở bằng IntelliJ IDEA

1. **File → Open** → chọn thư mục có `pom.xml` → **Open as Project/Trust Project** nếu được hỏi.
2. Đặt **Project SDK = JDK 17 hoặc 21**. Trong cửa sổ Maven, bấm **Reload All Maven Projects** và chờ tải xong.
3. Mở `src/main/java/vn/edu/auction/server/ServerMain.java`. Chạy `main`, với **Program arguments = `--demo`**. Có sẵn cấu hình `.run/1 - Server.run.xml`.
4. Khi console báo **Máy chủ đã sẵn sàng tại 127.0.0.1:5055**, chạy `src/main/java/vn/edu/auction/client/ClientLauncher.java`. Có cấu hình `.run/2 - Giao dien.run.xml`.
5. Muốn hai client, bật **Allow multiple instances** trong cấu hình Run của client, hoặc mở `2_MO_UNG_DUNG.bat` lần nữa.

Không cần tự tải JavaFX SDK, không cần thêm `--module-path` trong cách chạy này. Maven chọn thư viện native theo hệ điều hành hiện tại. Đừng dùng thư mục `target` được build từ hệ điều hành khác.

## Linux / macOS và dòng lệnh

Cài JDK 17/21 và `curl`. Trên Linux cần môi trường desktop và thư viện GTK 3. Mở hai terminal trong thư mục dự án:

```bash
bash scripts/run.sh server
```

```bash
bash scripts/run.sh client
```

Nếu đã cài Maven, có thể tự build rồi chạy:

```bash
mvn -B package -DskipTests
java -cp "target/classes:target/lib/*" vn.edu.auction.server.ServerMain --demo
# Chạy lệnh dưới ở terminal thứ hai:
java -cp "target/classes:target/lib/*" vn.edu.auction.client.ClientLauncher
```

Trên Windows, đổi dấu phân cách classpath `:` thành `;`:

```powershell
java -cp "target/classes;target/lib/*" vn.edu.auction.server.ServerMain --demo
java -cp "target/classes;target/lib/*" vn.edu.auction.client.ClientLauncher
```

## Chức năng đã tích hợp

- Đăng ký, đăng nhập, session riêng cho từng kết nối, phân quyền tại server; mật khẩu băm PBKDF2 có salt.
- Danh sách, tìm kiếm, lọc theo danh mục/trạng thái, sắp xếp, chi tiết sản phẩm và lịch sử.
- Người bán tạo phiên; sửa/xóa trước khi bắt đầu; quản lý các phiên của mình.
- Đặt giá trực tiếp, kiểm tra bước giá, thời hạn, số dư và quyền người đặt. Người bán không được tự đấu giá sản phẩm của mình.
- Giữ tiền của người dẫn đầu, nhả khoản giữ khi bị vượt; người thắng thanh toán mô phỏng; hủy phiên hợp lệ hoàn khoản giữ.
- Scheduler mở/đóng phiên, xác định người thắng duy nhất, trạng thái OPEN → RUNNING → FINISHED → PAID/CANCELED.
- Observer phát sự kiện sau commit qua Socket; client cập nhật giá, biểu đồ và ví theo sự kiện, không polling giá liên tục.
- Auto-bid với `maxBid`, `increment`, `PriorityQueue`; trần giá bị giới hạn bởi số dư; ưu tiên đăng ký sớm khi bằng trần.
- Anti-sniping tùy chọn: giá hợp lệ trong 30 giây cuối kéo dài hạn chót thêm 60 giây.
- Biểu đồ đường theo timestamp, danh sách theo dõi, phiên đã tham gia/đã thắng, thông báo có trạng thái đọc.
- Admin xem thống kê, tài khoản, khóa/mở khóa có điều kiện, quản lý phiên và nhật ký thao tác.
- Lưu H2 trên đĩa; JUnit, kiểm thử Socket thực, kiểm thử JavaFX headless, JaCoCo và cấu hình GitHub Actions.

Xem đối chiếu từng tiêu chí trong [docs/DOI_CHIEU_DE_BAI.md](docs/DOI_CHIEU_DE_BAI.md).

## Kiến trúc và quy tắc nghiệp vụ

| Thành phần | Vị trí |
|---|---|
| JavaFX, FXML, CSS và controller màn hình | `client/`, `resources/ui/` |
| DTO và JSON request/response/event | `protocol/` |
| Socket, session, scheduler, service, Observer, Singleton | `server/` |
| EntityManager, transaction, truy vấn | `dao/` |
| Entity, enum, các bất biến dữ liệu | `model/` |
| Factory Method, ngoại lệ, băm mật khẩu | `factory/`, `exception/`, `security/` |
| Kiểm thử | `src/test/java/vn/edu/auction/` |

Mọi tiền dùng `BigDecimal`. Chỉ server truy cập database. `AuctionManager` dùng khóa chung công bằng để tuần tự hóa giao dịch trong **một tiến trình server**; mỗi giao dịch cập nhật giá, ví, lịch sử và thông báo cùng commit hoặc rollback. JPA `@Version` bổ sung kiểm tra xung đột lưu trữ. Khóa không bao quanh việc gửi mạng.

Bản thiết kế giữ **User + BuyerProfile/SellerProfile/AdminProfile** theo ảnh UML được cung cấp: một tài khoản có thể vừa mua vừa bán. Kế thừa và đa hình thể hiện ở `BaseEntity`, `Item` và các loại sản phẩm. Xem [docs/KIEN_TRUC.md](docs/KIEN_TRUC.md) và [docs/HUONG_DAN_MODEL.md](docs/HUONG_DAN_MODEL.md).

## Kiểm thử và chất lượng mã

```bash
# Nghiệp vụ, model, database, Socket thật; không cần màn hình
mvn -B verify

# Toàn bộ, gồm JavaFX headless và ngưỡng line coverage >= 60%
mvn -B clean verify -Pui-tests

# Sửa định dạng Google Java Format trước khi commit
mvn spotless:apply
```

Kết quả thực tế đính kèm ở [docs/KET_QUA_KIEM_THU.md](docs/KET_QUA_KIEM_THU.md). Khi tự chạy, mở `target/site/jacoco/index.html` xem coverage và `target/surefire-reports/` xem từng test. Ảnh chụp giao diện tạo bởi test nằm trong `target/ui-screenshots/`.

Workflow `.github/workflows/ci.yml` chạy build, test, kiểm tra format và coverage khi push/PR. **Chưa có repository GitHub của nhóm được cung cấp**, nên không có tuyên bố CI đã chạy xanh trên GitHub. Sau khi push, kiểm tra tab Actions. Lịch sử commit và đóng góp thành viên cần phản ánh công việc thật; không được tạo lịch sử giả. Hướng dẫn Git ở [docs/GIT_VA_BAO_VE.md](docs/GIT_VA_BAO_VE.md).

## Dữ liệu và phạm vi triển khai

- File dữ liệu: `data/auctiva.mv.db`. Tắt server trước khi sao lưu cả thư mục `data`.
- Server mặc định chỉ nghe `127.0.0.1:5055`. Xem hướng dẫn chạy LAN trong tài liệu sử dụng.
- Mỗi server tối đa 64 kết nối; danh sách trả tối đa 500 phiên gần nhất, thông báo 100 mục, chi tiết tối đa 200 lượt giá và giao diện hiển thị 30 lượt gần nhất. Chưa có phân trang cho dữ liệu lớn.
- Auto-bid không giữ toàn bộ giá trần; chỉ giữ giá đang dẫn. Nếu tiền đang bị giữ ở phiên khác, mức cạnh tranh tự động giảm theo số dư thực tế. Khi nạp thêm tiền, có thể lưu lại cấu hình auto-bid để đánh giá ngay.
- Khoản giữ của người thắng được duy trì đến khi thanh toán hoặc người bán/admin hủy phiên; chưa có thời hạn thanh toán tự động.
- Đây là ứng dụng phục vụ học tập, dùng ví mô phỏng. Socket hiện chưa có TLS, chưa có xác thực email/khôi phục mật khẩu hay cổng thanh toán thật; dùng trên máy cá nhân hoặc mạng thực hành tin cậy. Chưa thiết kế cho nhiều tiến trình server cùng dùng DB.
- Đã kiểm thử JavaFX headless và server trên Linux/JDK 17. Cần chạy thử các file BAT trên máy Windows thực tế của nhóm trước buổi bảo vệ.

## Tài liệu đi kèm

- [Chạy và sử dụng từng bước](docs/CHAY_VA_SU_DUNG.md)
- [Kiến trúc, thuật toán và giao thức](docs/KIEN_TRUC.md)
- [Phần Model theo sơ đồ](docs/HUONG_DAN_MODEL.md)
- [Đối chiếu đề bài](docs/DOI_CHIEU_DE_BAI.md)
- [Kịch bản demo](docs/KICH_BAN_DEMO.md)
- [Kiểm thử và giới hạn xác minh](docs/KET_QUA_KIEM_THU.md)
- [Git và câu hỏi bảo vệ](docs/GIT_VA_BAO_VE.md)
