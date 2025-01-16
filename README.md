# NotDefteri
# NotDefteri - Kişisel Not Defteri Sistemi

Bu proje, kullanıcıların kişisel notlarını oluşturması, düzenlemesi ve yönetmesi için tasarlanmış bir Java uygulamasıdır. Notları kategorize etmek, etiketlemek ve durumlarını (taslak, tamamlanmış vb.) yönetmek gibi işlevler sunar. Projede farklı tasarım desenleri kullanılarak modüler bir yapı oluşturuldu. Proje ekibi sayfanın sonunda bulunmaktadır.

 *Özellikler:*

- *Not Yönetimi*: Kullanıcılar notlarını ekleyebilir, düzenleyebilir ve silebilir.
- *Durum Yönetimi*: Notların durumları “Taslak”, “Tamamlanmış” veya “Arşivlenmiş” gibi statülere atanabilir.
- *Etiketleme*: Notlar etiketlerle kategorize edilebilir.
- *Veri Tabanı Entegrasyonu*: Notlar, MySQL veri tabanında saklanır.
- *Tema Desteği*: Kullanıcılar farklı temalar arasında geçiş yapabilir.
- *Analiz*: Kullanıcı hareketlerini izlemek ve analiz raporları oluşturmak.

 *Gereksinimler:*

Projeyi çalıştırmak için aşağıdaki yazılım ve araçların sisteminizde kurulu olması gerekir:

- *Java Sürümü*: Java 17 veya daha yüksek.
- *Veri Tabanı*: MySQL Server.
- *Bağımlıklar*:
  - mysql-connector-j-9.1.0.jar (Proje ile birlikte verilmiştir.)
- *IDE*: IntelliJ IDEA veya benzeri bir Java geliştirme ortamı.

 *Kurulum:*

1. Proje dosyalarını bu depodan bilgisayarınıza indirin ve bir dizine çıkarın.

2. IDE'nizi kullanarak projeyi açın.

3. MySQL Connector JAR dosyasını projenize dahil edin:

   - IntelliJ IDEA kullanıyorsanız, "File > Project Structure > Libraries" yolunu takip ederek JAR dosyasını ekleyin.

4. Veri tabanı ayarlarını yapılandırın:

   - src/Model/DatabaseConnection.java dosyasında, MySQL sunucu bilgilerinizi (kullanıcı adı, şifre, veri tabanı adı) güncelleyin.

5. MySQL sunucusunda gerekli tabloları oluşturun. Bunun için şu SQL komutlarını kullanabilirsiniz:

   sql
   CREATE DATABASE NotDefteri;
   USE NotDefteri;

   CREATE TABLE Notes (
       id INT AUTO_INCREMENT PRIMARY KEY,
       title VARCHAR(255) NOT NULL,
       content TEXT,
       status ENUM('Draft', 'Completed', 'Archived') DEFAULT 'Draft',
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   );

   CREATE TABLE Tags (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL UNIQUE
   );

   CREATE TABLE NoteTags (
       note_id INT,
       tag_id INT,
       PRIMARY KEY (note_id, tag_id),
       FOREIGN KEY (note_id) REFERENCES Notes(id),
       FOREIGN KEY (tag_id) REFERENCES Tags(id)
   );

    CREATE TABLE `users` (
      `id` int(11) NOT NULL,
      `username` varchar(255) NOT NULL,
      `password` varchar(255) NOT NULL,
      `email` varchar(255) NOT NULL,
      `user_type` enum('user','admin') DEFAULT 'user',
      `created_at` timestamp NOT NULL DEFAULT current_timestamp()
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

   

6. Projeyi derleyin ve çalıştırın.

 *Kullanım:*

1. Ana sınıf (“Main.java”) üzerinden uygulamayı başlatın.
2. Kullanıcı arayüzünden aşağıdaki işlemleri gerçekleştirin:
   - Yeni not ekleyin.
   - Var olan bir notu düzenleyin veya silin.
   - Notlarınıza etiket ekleyerek kategorize edin.
   - Notların durumlarını (“Taslak”, “Tamamlanmış”, “Arşivlenmiş”) değiştirin.
   - Tema ayarlarını değiştirerek arayüzü kişiselleştirin.
3. *Filtreleme*: Notları belirli kriterlere göre filtrelemek veya sıralamak için farklı stratejiler arasından seçim yapabilirsiniz. Örneğin, tamamlanmış notları listeleyebilir veya etiketlere göre arama yapabilirsiniz.
4. *Analiz*: Kullanıcı hareketleri kaydedilir ve analiz sonuçları ile raporlanır.

*Proje Yapısı:*

- src klasörü: Kaynak kodların bulunduğu klasör.
  - analytics: Kullanıcı hareketlerinin analizini yapar.
  - Command: Komut tasarım deseninin uygulandığı bölüm.
  - Controller: MVC deseninde kontrol mantığını barındırır.
  - Model: Veri tabanı bağlantısı ve temel veri yapılarını içerir.
  - State: Notların durum yönetimi için kullanılır.
  - Strategy: Farklı filtreleme stratejilerini uygular.
  - ThemeFactory: Tema yönetimi.
  - View: Kullanıcı ara yüzünü barındırır.

-- *Tasarım Desenleri:* --

Bu projede aşağıdaki tasarım desenleri uygulanmıştır:

1. Command Deseni:

   - Kullanıcının not ekleme, silme ve güncelleme gibi işlemleri komut olarak ele alınır.
   - Her işlem, geri alınabilir veya yeniden uygulanabilir şekilde organize edilmiştir.

2. Observer Deseni:

   - Kullanıcı arayüzü, notlardaki değişikliklere anında tepki verir.
   - Örneğin, bir notun durumu değiştiğinde bu durum arayüze otomatik olarak yansır.

3. State Deseni:

   - Notların durumlarını (“Taslak”, “Tamamlanmış”, “Arşivlenmiş”) yönetir.
   - Durum değişiklikleri sırasında ilgili davranışlar (ör. bir taslak notu tamamlanmış olarak işaretleme) uygulanır.

4. Strategy Deseni:

   - Notları filtreleme ve sıralama işlemlerinde farklı stratejiler sunar.
   - Örneğin, notları tarih sırasına göre sıralamak veya yalnızca belirli bir etikete sahip notları listelemek için kullanılabilir.

5. Factory Deseni:

   - Tema oluşturma işlemlerinde kullanılır.
   - Kullanıcıların seçimine göre farklı arayüz temaları oluşturulur (ör. açık veya koyu tema).

6. Singleton Deseni:
   
   - DatabaseConnection sınıfı için uygulanmıştır ve tek bir veritabanı bağlantısı sağlar.
     
-- *Test:* --

Projenin test edilmesi için aşağıdaki adımları takip edin:

1. src/tests klasörü altındaki test sınıflarını kullanarak birim testlerini çalıştırın.
2. Her bir modül (Command, State, Strategy vb.) için ayrı test senaryoları mevcuttur.
3. Veri tabanı işlemleri için MockDatabaseConnection kullanarak bağımsız testler yapın.


-- *Proje Ekibi:* --

- *Kadir Ir*: [GitHub Profili](https://github.com/KadirIR)
- *Didem Gümüş*: [GitHub Profili](https://github.com/DidemGumus)
