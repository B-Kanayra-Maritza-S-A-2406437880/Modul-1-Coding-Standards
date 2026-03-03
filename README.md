# Modul 1- Adpro- Reflection

- Name : Kanayra Maritza Sanika Adeeva
- NPM : 2406437880
- Class : Adpro B

# Reflection 1
Di Latihan ini, saya mengimplementasi 2 fitur baru yaitu edit product dan delete product dengan memisahkan tanggung awab ke layer repository, service, dan controller sesuai dengan prinsip separation of concerns yang sudah dipelajari diman tiap layer punya peran yang jelas.
Setiap method di Controller dan Service fokus melakukan satu hal saja (Single Responsibility). Misalnya, ProductController hanya bertugas mengatur navigasi dan memparsing data, sementara logika penyimpanan ada di Repository. Selanjutnya, alih-alih menggunakan ID numerik atau string, saya menggunakan UUID.randomUUID() di ProductRepository untuk mencegah serangan ID Enumeration di mana user mencoba menebak-nebak URL produk orang lain. Terakhir, semua manipulasi data produk dilakukan melalui lapisan Service, sehingga Controller tidak punya akses langsung untuk mengacak-acak ArrayList di repositori. Namun, setelah saya telusuri masih ada beberapa hal yang harus diimprove antara lain adalah: 
Saat ini, method update mengembalikan null jika produk dengan ID tertentu tidak ditemukan. Ke depannya mekanisme ini dapat diperbaiki dengan penggunaan exception handling yang lebih eksplisit agar alur error lebih jelas dan aman. Selain itu, perlu konsistensi dalam validasi input agar tidak terjadi potensi kesalahan ketika ID yang diberikan tidak valid atau tidak tersedia di repository.

# Reflection 2
Setelah menulis unit test, saya merasa lebih percaya diri terhadap perilaku setiap komponen dalam aplikasi. Hal itu membantu saya memastikan bahwa setiap fitur fitur yang dibuat bekerja sesuai dengan spesifikasinya, dan kalua ada kesalahan logika dapat terdeteksi lebih awal sebelum aplikasi dijalankan secara keseluruhan. Terkait jumlah unit test dalam satu class menurut saya sebaiknya tidak ditentukan secara pasti, melainkan disesuaikan dengan jumlah perilaku yang ingin diuji. Setiap method idealnya memiliki beberapa test case yang mencakup skenario positif, skenario negatif, edge cases.Memiliki 100% code coverage tidak berarti kode tersebut bebas dari bug atau error melainkan coverage hanya mengukur bagian kode yang dijalankan oleh test, bukan benar benar menjamin bahwa seluruh logika sudah benar atau semua kemungkinan kesalahan telah diuji.

Jika setelah menulis CreateProductFunctionalTest.java saya diminta untuk membuat functional test baru yang memverifikasi jumlah item di product list dibuat dalam class baru dengan setup dan instance variable yang sama, hal ini berpotensi mengurangi kebersihan kode yaitu karena adanya duplikasi kode . Duplikasi kode ini khususnya terjadi pada bagian setup seperti inisialisasi baseUrl, @LocalServerPort, serta konfigurasi @SpringBootTest dmana duplikasi ini dapat menurunkan maintainability karena perubahan kecil pada setup harus dilakukan di banyak tempat. Sarann improvementt sya untuk meningkatkan kualitas kode ke depanny adalah dengan membuat base functional test class yang berisi setup umum, kemudian functional test lain dapat meng-inherit class tersebut. Dengan cara ini, kode menjadi lebih bersih, konsisten, dan mudah dikembangkan tanpa mengubah perilaku test yang ada.


#  Modul 2 - Adpro- Reflection

# Question 1:
Berikut adalah perbaikan yang dilakukan terkait Code Quality Issues SonarCloud:

1. Arsitektur & Dependency Injection
- Issue: Penggunaan @Autowired pada field injection di ProductServiceImpl dan ProductController
- Fix: Menghapus bagian "@Autowired" dan beralih ke Constructor Injection dengan menambahkan constructor publik dan juga mengubah modifier dependency menjadi private final
Hal ini untuk menghilangkan dependensi tersembunyi, memastikan immutability, dan mempermudah unit testing

2. Reliability & Accessibility (HTML Issues)
- Issue: Form label pada EditProduct.html tidak terasosiasi dengan kontrol input (Pesan log : "A form label must be associated with a control"
- Fix: Menambahkan atribut yang menyelaraskan <label> dengan elemen <input> terkait menggunakan "label for".

3. Kualitas Unit Testing
- Issue: Adanya metode tes kosong seperti contextLoads() dan testMain() yang tidak memiliki verifikasi. Oleh karena itu, saya diminta untuk "Add at least one assertion to this test case"
- Fix: Menambahkan assertDoesNotThrow() pada testMain() untuk memastikan aplikasi dapat start tanpa crash serta menambahkan komentar dokumentasi yang menjelaskan tujuan dari metode contextLoads().

4. Hygiene & Maintainability (Code Smells)
- Issue: Unused imports dan struktur dependensi yang berantakan.
- Fix: Menghapus unused imports seperti org.junit.jupiter.api.BeforeEach dan java.util.Iterator serta merapikan file build.gradle.kts dengan mengelompokkan dependensi berdasarkan tujuannya
Hal ini untuk mempercepat proses kompilasi dengan membuang library yang tidak terpakai.

# Question 2:
Implementasi saat ini sudah memenuhi syarat Continuous Integration dan Continuous Deployment karena setiap kali saya melakukan push kode ke branch module-2-exercise atau melakukan merge ke branch main, GitHub Actions secara otomatis menjalankan rangkaian pengetesan. Hasil tes kemudian dilaporkan ke SonarCloud untuk dianalisis apakah terdapat _code smells_ atau kerentanan keamanan. Selain itu, saya memastikan bahwa setiap perubahan harus lulus verifikasi JaCoCo dengan _code coverage_ 100%, yang menjamin bahwa setiap baris kode yang baru dibuat telah diuji secara menyeluruh sebelum dianggap stabil. Munculnya centang hijau pada log GitHub menjadi indikator kuat bahwa kode sudah terverifikasi aman untuk digabungkan ke branch main. Melalui integrasi langsung antara repositori GitHub dengan platform Koyeb, setiap kode yang masuk ke branch main dan lolos tahap build akan langsung dideploy ke server produksi. Status "Koyeb Deployment is healthy" menjadi bukti bahwa versi terbaru aplikasi sudah live dan dapat diakses oleh pengguna. Hal ini sudah menerapkan konsep Continuous Deployment. Jika terdapat kegagalan pada tahap pengetesan atau syarat coverage tidak terpenuhi, sistem akan memberikan tanda silang merah dan menghentikan proses deployment untuk memastikan aplikasi yang rusak tidak sampai ke client serta menjaga kualitas aplikasi dari awal hingga akhir.

#  Modul 3 - Adpro- Reflection
# Question 1 (Principles applied to this project):
1. Single Responsibility Principle (SRP):
I decoupled CarController from ProductController by removing the inheritance ("extends") and moving them into separate files. Previously, ProductController was responsible for both handling Product-related HTTP requests and acting as a base class for CarController. This meant the class had multiple reasons to change. By refactoring, I moved the Car-related logic into its own independent CarController, ensuring ProductController only focuses on one domain.

2. Open-Closed Principle (OCP):
I implemented an abstract BaseModel class that handles UUID generation. This makes the system open for extension (we can add new models simply by extending BaseModel) but closed for modification (we don't need to change the ID generation logic in the existing classes). Standardizing the ID generation in a base class prevents code duplication. The abstract model is extended by the Product and Car model.

3. Liskov Substitution Principle (LSP):
I applied LSP by removing the extends ProductController from CarController. In the initial code, CarController was forced to inherit a ProductService via super(service) that it did not use. Since CarController cannot meaningfully substitute ProductController, I separated them to ensure the subclass doesn't carry irrelevant dependencies. Additionally, by using BaseModel, both Car and Product can now be treated as consistent entities that share a common ID property.

4. Dependency Inversion Principle (DIP):
I refactored the project so that Controllers and Services depend on Interfaces rather than concrete classes. I created CarRepositoryInterface and ProductRepositoryInterface, and ensured that the Services are injected as interfaces. This ensures that high-level modules are not tightly coupled to low-level modules, making the code more flexible and easier to test.

5. Interface Segregation Principle (ISP)
By maintaining separate interfaces for CarService and ProductService, I ensured that each controller only interacts with the methods it actually requires, preventing "fat interfaces".

# Question 2 (Advantages after applying SOLID principles)
- Maintainability through Abstraction
By creating BaseModel class, we standardized the ID system. After I applied the SOLID principles I designed a scenario that Product and Car can simply inherit BaseModel. If we want to add another model, we don't need to write manual ID generation logic. We just extend BaseModel and it's ready.
In contrast to the before solid code where Car used String and Product used UUID separately.

= Flexibility and Swappable Logic
After applying the SOLID principle, in CarServiceImpl, the code now depends on the interface. If we want to move from an ArrayList storage to a real database, we only need to create a new implementation of CarRepositoryInterface. We won't have to change a single line of code in the CarServiceImpl or CarController.

- Safety from Side Effects (SRP & LSP):
Now that CarController is independent, we can change the logic in ProductController (like example adding new validation) without any risk of breaking the Car functionality. They are no longer "physically" tied by inheritance.

# Question 3 (Disadvantages of not applying SOLID principles)
- Fragility due to Improper Inheritance
In the Before code, CarController extends ProductController. The CarController constructor was forced to call super(productSservice). If someone modified the ProductService or the ProductController constructor, the CarController would be fail to compile even though it doesn't actually use the ProductService.

- Code Duplication & Inconsistency
Without BaseModel, the logic was scattered. Before applying the SOLID principles, CarRepository had a static int id and used UUID.randomUUID().toString(), while ProductRepository handled UUID directly. This inconsistency makes the code "Rigid" if we wanted to change the ID format to something else. We would have to find and manually change every single repository one by one.

- Tight Coupling
Before SOLID, CarServiceImpl was directly dependent on the concrete CarRepository. If we wanted to run a unit test for the service layer, we couldn't easily "mock" the repository because it was dependent to the concrete class. This makes the code hard to test and hard to maintain as the project grows.

- God Class Complexity
The old ProductController was doing too much. It was managing Product routes and serving as the base for Car routes. This "Double Responsibility" makes the file grow twice as fast, making it much harder for a us to navigate the code and increasing the chance of accidental bugs when editing the file.
