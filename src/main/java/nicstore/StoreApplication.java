package nicstore;

import nicstore.Models.Category;
import nicstore.Models.Product;
import nicstore.Models.ProductImage;
import nicstore.repository.CategoryRepository;
import nicstore.repository.ProductImageRepository;
import nicstore.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@SpringBootApplication
public class StoreApplication implements WebMvcConfigurer {

    @Value("${path_for_product_image}")
    private String PATH_FOR_PRODUCTS_IMAGE;

    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public ApplicationRunner dataLoader(CategoryRepository categoryRepo,
                                        ProductRepository productRepo,
                                        ProductImageRepository productImageRepo
    ) {
        return args -> {
            Category[] categories = new Category[]{
                    new Category(1L, "Смартфоны", null),
                    new Category(2L, "Аудиотехника", null),
                    new Category(3L, "ТВ, консоли и аудио", null)
            };
            Category[] smartphones = new Category[]{
                    new Category(4L, "Apple", categories[0]),
                    new Category(5L, "Samsung", categories[0]),
                    new Category(6L, "Сопутствующие товары", categories[0])
            };

            Category[] addGoodsForSmartphones = new Category[]{
                    new Category(7L, "Наушники", smartphones[2]),
                    new Category(8L, "Чехлы", smartphones[2]),
                    new Category(9L, "Накладки", smartphones[2])
            };

            Category[] audio = new Category[]{
                    new Category(10L, "Портативные колонки", categories[1]),
                    new Category(11L, "Колонки", categories[1]),
                    new Category(12L, "Наушники", categories[1])
            };

            Category[] audioHeadphones = new Category[]{
                    new Category(13L, "Беспроводные наушники", audio[2]),
                    new Category(14L, "Проводные наушники", audio[2])
            };

            Category[] televisions = new Category[]{
                    new Category(15L, "Телевизоры", categories[2]),
                    new Category(16L, "Домашний кинотеатр, проекторы", categories[2]),
                    new Category(17L, "Консоли и видеоигры", categories[2])
            };
            Category[] smartphonesHeadphones = new Category[]{
                    new Category(18L, "Беспроводные наушники", addGoodsForSmartphones[0]),
                    new Category(19L, "Проводные наушники", addGoodsForSmartphones[0])
            };


            ProductImage[] images = {
                    ProductImage.builder().id(1L).path(PATH_FOR_PRODUCTS_IMAGE + "/1/iphone13.jpg").name("iPhone 13").build(),
                    ProductImage.builder().id(2L).path(PATH_FOR_PRODUCTS_IMAGE + "/2/iphone13pro.jpg").name("iPhone 13 Pro").build(),
                    ProductImage.builder().id(3L).path(PATH_FOR_PRODUCTS_IMAGE + "/3/iphone14.jpg").name("iPhone 14").build(),
                    ProductImage.builder().id(4L).path(PATH_FOR_PRODUCTS_IMAGE + "/4/iphone14pro.jpg").name("iPhone 14 Pro").build(),
                    ProductImage.builder().id(5L).path(PATH_FOR_PRODUCTS_IMAGE + "/5/iphone15.jpg").name("iPhone 15").build(),
                    ProductImage.builder().id(6L).path(PATH_FOR_PRODUCTS_IMAGE + "/6/iphone15pro.jpg").name("iPhone 15 Pro").build(),
                    ProductImage.builder().id(7L).path(PATH_FOR_PRODUCTS_IMAGE + "/7/iphone11.jpg").name("iPhone 11").build(),
                    ProductImage.builder().id(8L).path(PATH_FOR_PRODUCTS_IMAGE + "/7/iphone11-2.jpg").name("iPhone 11").build(),
                    ProductImage.builder().id(9L).path(PATH_FOR_PRODUCTS_IMAGE + "/8/iphone11pro.jpg").name("iPhone 11 Pro").build(),
                    ProductImage.builder().id(10L).path(PATH_FOR_PRODUCTS_IMAGE + "/8/iphone11pro-2.jpg").name("iPhone 11 Pro").build(),
                    ProductImage.builder().id(11L).path(PATH_FOR_PRODUCTS_IMAGE + "/9/iphone12.jpg").name("iPhone 12").build(),
                    ProductImage.builder().id(12L).path(PATH_FOR_PRODUCTS_IMAGE + "/9/iphone12-2.jpg").name("iPhone 12").build(),
                    ProductImage.builder().id(13L).path(PATH_FOR_PRODUCTS_IMAGE + "/10/iphone12promax.jpg").name("iPhone 12 PRO").build(),
                    ProductImage.builder().id(14L).path(PATH_FOR_PRODUCTS_IMAGE + "/10/iphone12promax-2.jpg").name("iPhone 12 PRO").build(),
                    ProductImage.builder().id(15L).path(PATH_FOR_PRODUCTS_IMAGE + "/11/samsunggalaxys21fe.jpg").name("Samsung Galaxy S21 FE").build(),
                    ProductImage.builder().id(16L).path(PATH_FOR_PRODUCTS_IMAGE + "/11/samsunggalaxys21fe-2.jpg").name("Samsung Galaxy S21 FE").build(),
                    ProductImage.builder().id(17L).path(PATH_FOR_PRODUCTS_IMAGE + "/12/samsunggalaxya54.jpg").name("Samsung Galaxy A54").build(),
                    ProductImage.builder().id(18L).path(PATH_FOR_PRODUCTS_IMAGE + "/13/samsunggalaxys23ultra.jpg").name("Samsung Galaxy S23 Ultra").build(),
                    ProductImage.builder().id(19L).path(PATH_FOR_PRODUCTS_IMAGE + "/14/samsunggalaxys22ultra.jpg").name("Samsung Galaxy S22 Ultra").build(),
                    ProductImage.builder().id(20L).path(PATH_FOR_PRODUCTS_IMAGE + "/15/pocof5.jpg").name("POCO F5").build(),
                    ProductImage.builder().id(21L).path(PATH_FOR_PRODUCTS_IMAGE + "/15/pocof5-2.jpg").name("POCO F5").build(),
                    ProductImage.builder().id(22L).path(PATH_FOR_PRODUCTS_IMAGE + "/16/pocom4pro.jpg").name("POCO M4 Pro").build(),
                    ProductImage.builder().id(23L).path(PATH_FOR_PRODUCTS_IMAGE + "/17/huaweinova11pro.jpg").name("Huawei Nova 11 Pro").build(),
                    ProductImage.builder().id(24L).path(PATH_FOR_PRODUCTS_IMAGE + "/18/huaweip50.jpg").name("Huawei P 50").build(),
                    ProductImage.builder().id(25L).path(PATH_FOR_PRODUCTS_IMAGE + "/20/airpodspro.jpg").name("TWS Apple AirPods Pro").build(),
					ProductImage.builder().id(26L).path(PATH_FOR_PRODUCTS_IMAGE + "/21/airpodspro2.jpg").name("TWS Apple AirPods Pro 2").build(),
					ProductImage.builder().id(27L).path(PATH_FOR_PRODUCTS_IMAGE + "/22/huaweifreebuds5i.jpg").name("TWS Huawei Freebuds 5i").build(),
					ProductImage.builder().id(28L).path(PATH_FOR_PRODUCTS_IMAGE + "/23/honorchoiceearbusx3.jpg").name("TWS Honor Choice Earbuds X3").build(),
					ProductImage.builder().id(29L).path(PATH_FOR_PRODUCTS_IMAGE + "/24/logitechg435lighspeed.jpg").name("Logitech G435 LIGHTSPEED").build(),
					ProductImage.builder().id(30L).path(PATH_FOR_PRODUCTS_IMAGE + "/25/fifineh6.jpg").name("Fifine H6").build(),
					ProductImage.builder().id(31L).path(PATH_FOR_PRODUCTS_IMAGE + "/26/logitechg735.jpg").name("Logitech G735").build(),
					ProductImage.builder().id(32L).path(PATH_FOR_PRODUCTS_IMAGE + "/27/appleearpods.jpg").name("Apple EarPods (Lightning)").build(),
					ProductImage.builder().id(33L).path(PATH_FOR_PRODUCTS_IMAGE + "/28/redlinee01.jpg").name("Red Line E01").build(),
					ProductImage.builder().id(34L).path(PATH_FOR_PRODUCTS_IMAGE + "/29/epossennhizerpc8.jpg").name("EPOS Sennheiser PC8").build(),
					ProductImage.builder().id(35L).path(PATH_FOR_PRODUCTS_IMAGE + "/30/jblxtreme3.jpg").name("JBL Xtreme 3").build(),
					ProductImage.builder().id(36L).path(PATH_FOR_PRODUCTS_IMAGE + "/31/jblcharge5.jpg").name("JBL Charge 5").build(),
					ProductImage.builder().id(37L).path(PATH_FOR_PRODUCTS_IMAGE + "/32/jblboombox3.jpg").name("JBL BoomBox 3").build(),
					ProductImage.builder().id(38L).path(PATH_FOR_PRODUCTS_IMAGE + "/33/yandexstationlight.jpg").name("Яндекс Станция Лайт").build(),
					ProductImage.builder().id(39L).path(PATH_FOR_PRODUCTS_IMAGE + "/34/yandexstation2.jpg").name("Яндекс Станция 2").build(),
					ProductImage.builder().id(40L).path(PATH_FOR_PRODUCTS_IMAGE + "/35/yandexstationmini.jpg").name("Яндекс Станция мини").build(),
					ProductImage.builder().id(41L).path(PATH_FOR_PRODUCTS_IMAGE + "/36/fierobhs-100.jpg").name("Fiero BHS-100").build(),
					ProductImage.builder().id(42L).path(PATH_FOR_PRODUCTS_IMAGE + "/37/edifiers3000pro.jpg").name("Edifier S3000Pro").build(),

                    ProductImage.builder().id(43L).path(PATH_FOR_PRODUCTS_IMAGE + "/38/ledxiaomimitvp132.jpg").name("Xiaomi MI TV P1 32").build(),
					ProductImage.builder().id(44L).path(PATH_FOR_PRODUCTS_IMAGE + "/39/ledxiaomimitvp132.jpg").name("LG 32LM576BPLD").build(),
					ProductImage.builder().id(45L).path(PATH_FOR_PRODUCTS_IMAGE + "/40/dexpa321.jpg").name("DEXP A321").build(),

					ProductImage.builder().id(46L).path(PATH_FOR_PRODUCTS_IMAGE + "/41/lglhb655nk.jpg").name("Домашний кинотеатр LG LHB655NK").build(),
					ProductImage.builder().id(47L).path(PATH_FOR_PRODUCTS_IMAGE + "/41/lglhb655nk-2.jpg").name("Домашний кинотеатр LG LHB655NK").build(),
					ProductImage.builder().id(48L).path(PATH_FOR_PRODUCTS_IMAGE + "/42/rombicarayboxb1.jpg").name("Проектор Rombica Ray Box B1").build(),
					ProductImage.builder().id(49L).path(PATH_FOR_PRODUCTS_IMAGE + "/43/rombicarayboxb1.jpg").name("Проектор Zodikam Z07A белый").build(),

					ProductImage.builder().id(50L).path(PATH_FOR_PRODUCTS_IMAGE + "/44/playstation4slim.jpg").name("Игровая консоль PlayStation 4 Slim").build(),
					ProductImage.builder().id(51L).path(PATH_FOR_PRODUCTS_IMAGE + "/45/playstation5digitaledition.jpg").name("Игровая консоль PlayStation 5 Digital Edition").build(),
					ProductImage.builder().id(52L).path(PATH_FOR_PRODUCTS_IMAGE + "/46/battlefiedl1ps4.jpg").name("Игра Battlefield 1 (PS4)").build(),
					ProductImage.builder().id(53L).path(PATH_FOR_PRODUCTS_IMAGE + "/47/microsoftxboxseriess.jpg").name("Игровая консоль Microsoft Xbox Series S").build(),
					ProductImage.builder().id(54L).path(PATH_FOR_PRODUCTS_IMAGE + "/48/microsoftxboxseriess.jpg").name("Игровая консоль Microsoft Xbox Series X").build(),
					ProductImage.builder().id(55L).path(PATH_FOR_PRODUCTS_IMAGE + "/49/eldenringxb.jpg").name("Игра Elden Ring – Launch Edition)").build(),


            };

            Product[] products = new Product[]{
                    Product.builder().id(1L).name("Смартфон Apple iPhone 13").images(Collections.singletonList(images[0])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(77997.0)).quantity(6).build(),
                    Product.builder().id(2L).name("Смартфон Apple iPhone 13 PRO").images(Collections.singletonList(images[1])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(90999.0)).quantity(5).build(),
                    Product.builder().id(3L).name("Смартфон Apple iPhone 14").images(Collections.singletonList(images[2])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(80999.0)).quantity(10).build(),
                    Product.builder().id(4L).name("Смартфон Apple iPhone 14 PRO").images(Collections.singletonList(images[3])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(89999.0)).quantity(3).description("У iPhone 14 Pro 6,1--дюймовая панель. Пиковая яркость — 2000 нит. Используется небольшой вырез с датчиком фронтальной камеры и системой Face ID. Кроме того, он способен визуально уменьшаться и увеличиваться. Предусмотрен режим Always-on Display (экран всегда включен)").build(),
                    Product.builder().id(5L).name("Смартфон Apple iPhone 15").images(Collections.singletonList(images[4])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(115999.0)).quantity(2).description("Базовый iPhone 15 получил корпус со скругленными краями и матовым покрытием. У смартфона 6,1-дюймовый OLED-дисплей с пиковой яркостью 2000 нит. Вместо челки в верхней части дисплея овальный разрез Dynamic Island.\n" +
                            "Тыльная камера двойная, с диагональным расположением датчиков. Главный сенсор 48-мегапиксельный, ширик 12-мегапиксельный с возможностью съемки с 2-кратным приближением без потери качества. Фронтальная камера 12-мегапиксельная. iPhone 15 умеет снимать видео в 4K при 60 fps. Есть специальный кинематографический режим съемки.")
                            .build(),
                    Product.builder().id(6L).name("Смартфон Apple iPhone 15 Pro").images(Collections.singletonList(images[5])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(135999.0)).quantity(2).description("Аппаратной основой Apple iPhone 15 Pro стал 3-нанометровый чипсет A17 Pro с 6-ядерным GPU и поддержкой трассировки лучей. Чип выдает высочайшую производительность в играх. Также имеется 16-ядерный Neural Engine, который обрабатывает 35 миллионов операций в секунду.\n" +
                            "Корпус смартфона изготовлен из титана 5-го класса (такой, например, используется в космическом ровере NASA). Титан прочнее, легче и долговечнее, чем алюминий и сталь. У iPhone 15 Pro нет физического переключателя для перехода в бесшумный режим. На его месте расположена программируемая кнопка, которая реагирует на силу нажатия. С ее помощью можно, например, включить камеру или фонарик.\n" +
                            "У iPhone 15 Pro 6,1-дюймовый OLED-дисплей с разрешением 2556 × 1179 пикселей, кадровой частотой 120 Гц и пиковой яркостью 2000 нит.")
                            .build(),
                    Product.builder().id(7L).name("Смартфон Apple iPhone 11").images(Arrays.asList(images[6], images[7])).categories(new HashSet<Category>() {{
                                add(smartphones[0]);
                            }}).price(BigDecimal.valueOf(52299.0)).description("Ничего лишнего. Только самое. Система двух камер не оставит никого из ваших друзей за кадром. Самый быстрый процессор iPhone и мощный аккумулятор позволят больше делать и тратить меньше времени на подзарядку. " +
                                    "А высочайшее качество видео на iPhone означает, что ваши истории станут ещё ярче и детальнее.")
                            .quantity(15).build(),
                    Product.builder().id(8L).name("Смартфон Apple iPhone 11 PRO").images(Arrays.asList(images[8], images[9])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(70999.0)).quantity(10).description("Смартфон Apple iPhone 11 Pro располагает тройной камерой с 12-мегапиксельной камерой, благодаря которой вам не придется подбирать благоприятные условия для предстоящих кадров." +
                            " Если вы внезапно забудете банковскую карту, причин для расстройства у вас не найдется – а все благодаря реализованной в устройстве технологии Apple Pay." +
                            " Безрамочный экран транслирует изображения, ролики и киноленты в максимальном разрешении 2436x1125 пикселей.")
                            .build(),
                    Product.builder().id(9L).name("Смартфон Apple iPhone 12").images(Arrays.asList(images[10], images[11])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(55999.0)).quantity(10).description("Это iPhone 12 mini. Мощные функции в небольшом корпусе с диагональю 5,4 дюйма. Невероятно яркий компактный дисплей Super Retina XDR. Передняя панель Ceramic Shield, с которой риск повреждений дисплея при падении в 4 раза ниже." +
                            " Потрясающее качество снимков при слабом освещении благодаря Ночному режиму на всех камерах. Съёмка, монтаж и воспроизведение HDR-видео кинематографического качества в стандарте Dolby Vision. Мощный процессор A14 Bionic. ")
                            .build(),
                    Product.builder().id(10L).name("Смартфон Apple iPhone 12 PRO Max").images(Arrays.asList(images[12], images[13])).categories(new HashSet<Category>() {{
                        add(smartphones[0]);
                    }}).price(BigDecimal.valueOf(89999.0)).quantity(10).description("Это iPhone 12 Pro Max. Увеличенный дисплей Super Retina XDR 6,7 дюйма. Передняя панель Ceramic Shield, с которой риск повреждений дисплея при падении в 4 раза ниже." +
                            " Потрясающее качество снимков при слабом освещении благодаря лучшей на iPhone системе камер Pro. ")
                            .build(),
                    Product.builder().id(11L).name("Смартфон Samsung Galaxy S21 FE").images(Arrays.asList(images[14], images[15])).categories(new HashSet<Category>() {{
                                add(smartphones[1]);
                            }}).price(BigDecimal.valueOf(45999.0)).description("Смартфон Samsung Galaxy S21 FE выполнен в тонком корпусе с классической расцветкой и предлагает широкие функциональные возможности флагманского уровня." +
                                    " В нем установлен безрамочный дисплей 6.4 дюйма на основе панели Dynamic AMOLED 2X (2340x1080 пикселей), на котором отображается детализированная и красочная картинка.")
                            .quantity(8).build(),
                    Product.builder().id(12L).name("Смартфон Samsung Galaxy A54").images(Collections.singletonList(images[16])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(41999.0)).quantity(8).build(),
                    Product.builder().id(13L).name("Смартфон Samsung Galaxy S23 Ultra").images(Collections.singletonList(images[17])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(108999.0)).quantity(4).build(),
                    Product.builder().id(14L).name("Смартфон Samsung Galaxy S22 Ultra").images(Collections.singletonList(images[18])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(95999.0)).quantity(5).build(),
                    Product.builder().id(15L).name("Смартфон POCO F5").images(Arrays.asList(images[19], images[20])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(42999.0)).quantity(8).build(),
                    Product.builder().id(16L).name("Смартфон POCO M4 Pro").images(Collections.singletonList(images[21])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(18999.0)).quantity(8).build(),
                    Product.builder().id(17L).name("Смартфон HUAWEI Nova 11 Pro").images(Collections.singletonList(images[22])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(28999.0)).quantity(8).build(),
                    Product.builder().id(18L).name("Смартфон HUAWEI P50").images(Collections.singletonList(images[23])).categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(44999.0)).quantity(15).build(),
                    Product.builder().id(19L).name("Смартфон OnePlus 11").categories(new HashSet<Category>() {{
                        add(categories[0]);
                    }}).price(BigDecimal.valueOf(69999.0)).quantity(5).build(),
					Product.builder().id(20L).name("TWS-наушники Apple AirPods Pro").images(Collections.singletonList(images[24])).categories(new HashSet<Category>() {{
						add(audioHeadphones[0]);
						add(addGoodsForSmartphones[0]);
					}}).price(BigDecimal.valueOf(11999.0)).quantity(25).build(),
					Product.builder().id(21L).name("TWS-наушники Apple AirPods Pro 2").images(Collections.singletonList(images[25])).categories(new HashSet<Category>() {{
						add(audioHeadphones[0]);
                        add(addGoodsForSmartphones[0]);
                    }}).price(BigDecimal.valueOf(12999.0)).quantity(50).build(),
					Product.builder().id(22L).name("Наушники TWS Huawei Freebuds 5i").images(Collections.singletonList(images[26])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[0]);
                        add(addGoodsForSmartphones[0]);
					}}).price(BigDecimal.valueOf(6399.0)).quantity(20).build(),
					Product.builder().id(23L).name("Наушники TWS Honor Choice Earbuds X3").images(Collections.singletonList(images[27])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[0]);
                        add(addGoodsForSmartphones[0]);
					}}).price(BigDecimal.valueOf(3399.0)).quantity(35).build(),
					Product.builder().id(24L).name("Радиочастотная гарнитура Logitech G435 LIGHTSPEED").images(Collections.singletonList(images[28])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[0]);
                        add(addGoodsForSmartphones[0]);
					}}).price(BigDecimal.valueOf(8999.0)).quantity(35).build(),
					Product.builder().id(25L).name("Проводная гарнитура Fifine H6").images(Collections.singletonList(images[29])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[1]);
                        add(addGoodsForSmartphones[1]);
					}}).price(BigDecimal.valueOf(4699.0)).quantity(15).build(),
					Product.builder().id(26L).name("Bluetooth-гарнитура Logitech G735 белый").images(Collections.singletonList(images[30])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[0]);
                        add(addGoodsForSmartphones[0]);
					}}).price(BigDecimal.valueOf(2599.0)).quantity(10).build(),
					Product.builder().id(27L).name("Проводная гарнитура Apple EarPods (Lightning)").images(Collections.singletonList(images[31])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[1]);
                        add(addGoodsForSmartphones[1]);
					}}).price(BigDecimal.valueOf(2100.0)).quantity(50).build(),
					Product.builder().id(28L).name("Проводная гарнитура Red Line E01").images(Collections.singletonList(images[32])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[1]);
                        add(addGoodsForSmartphones[1]);
					}}).price(BigDecimal.valueOf(199.0)).quantity(50).build(),
					Product.builder().id(29L).name("Проводная гарнитура EPOS Sennheiser PC8").images(Collections.singletonList(images[33])).categories(new HashSet<Category>() {{
                        add(audioHeadphones[1]);
                        add(addGoodsForSmartphones[1]);
					}}).price(BigDecimal.valueOf(4999.0)).quantity(10).build(),
					Product.builder().id(30L).name("Портативная колонка JBL Xtreme 3").images(Collections.singletonList(images[34])).categories(new HashSet<Category>() {{
                        add(audio[0]);
					}}).price(BigDecimal.valueOf(27499.0)).quantity(15).build(),
					Product.builder().id(31L).name("Портативная колонка JBL Charge 5").images(Collections.singletonList(images[35])).categories(new HashSet<Category>() {{
                        add(audio[0]);
					}}).price(BigDecimal.valueOf(14499.0)).quantity(15).build(),
					Product.builder().id(32L).name("Портативная колонка JBL BoomBox 3").images(Collections.singletonList(images[36])).categories(new HashSet<Category>() {{
                        add(audio[0]);
					}}).price(BigDecimal.valueOf(59499.0)).quantity(5).build(),
					Product.builder().id(33L).name("Умная колонка Яндекс Станция Лайт").images(Collections.singletonList(images[37])).categories(new HashSet<Category>() {{
                        add(audio[1]);
					}}).price(BigDecimal.valueOf(4999.0)).quantity(15).build(),
					Product.builder().id(34L).name("Умная колонка Яндекс Станция 2").images(Collections.singletonList(images[38])).categories(new HashSet<Category>() {{
                        add(audio[1]);
					}}).price(BigDecimal.valueOf(16999.0)).quantity(10).build(),
					Product.builder().id(35L).name("Умная колонка Яндекс Станция Мини с часами").images(Collections.singletonList(images[39])).categories(new HashSet<Category>() {{
                        add(audio[1]);
					}}).price(BigDecimal.valueOf(8999.0)).quantity(10).build(),
					Product.builder().id(36L).name("Колонки 2.0 Fiero BHS-100").images(Collections.singletonList(images[40])).categories(new HashSet<Category>() {{
                        add(audio[1]);
					}}).price(BigDecimal.valueOf(8999.0)).quantity(10).build(),
					Product.builder().id(37L).name("Колонки 2.0 Edifier S3000Pro").images(Collections.singletonList(images[41])).categories(new HashSet<Category>() {{
                        add(audio[1]);
					}}).price(BigDecimal.valueOf(62899.0)).quantity(5).build(),
					Product.builder().id(38L).name("Телевизор LED Xiaomi MI TV P1 32").images(Collections.singletonList(images[42])).categories(new HashSet<Category>() {{
                        add(televisions[0]);
					}}).price(BigDecimal.valueOf(14899.0)).quantity(4).build(),
					Product.builder().id(39L).name("Телевизор LED LG 32LM576BPLD").images(Collections.singletonList(images[43])).categories(new HashSet<Category>() {{
                        add(televisions[0]);
					}}).price(BigDecimal.valueOf(21999.0)).quantity(4).build(),
					Product.builder().id(40L).name("Телевизор LED DEXP A321").images(Collections.singletonList(images[44])).categories(new HashSet<Category>() {{
                        add(televisions[0]);
					}}).price(BigDecimal.valueOf(10699.0)).quantity(2).build(),

					Product.builder().id(41L).name("Домашний кинотеатр LG LHB655NK").images(Arrays.asList(images[45], images[46])).categories(new HashSet<Category>() {{
                        add(televisions[1]);
					}}).price(BigDecimal.valueOf(10699.0)).quantity(2).build(),
                    Product.builder().id(42L).name("Проектор Rombica Ray Box B1").images(Collections.singletonList(images[47])).categories(new HashSet<Category>() {{
                        add(televisions[1]);
					}}).price(BigDecimal.valueOf(8699.0)).quantity(3).build(),
                    Product.builder().id(43L).name("Проектор Zodikam Z07A").images(Collections.singletonList(images[48])).categories(new HashSet<Category>() {{
                        add(televisions[1]);
					}}).price(BigDecimal.valueOf(11699.0)).quantity(5).build(),
                    Product.builder().id(44L).name("Игровая консоль PlayStation 4 Slim").images(Collections.singletonList(images[49])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(39799.0)).quantity(7).build(),
                    Product.builder().id(45L).name("Игровая консоль PlayStation 5 Digital Edition").images(Collections.singletonList(images[50])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(55799.0)).quantity(9).build(),
                    Product.builder().id(46L).name("Игра Battlefield 1 (PS4)").images(Collections.singletonList(images[51])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(55799.0)).quantity(9).build(),
                    Product.builder().id(47L).name("Игровая консоль Microsoft Xbox Series S").images(Collections.singletonList(images[52])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(30799.0)).quantity(5).build(),
                    Product.builder().id(48L).name("Игровая консоль Microsoft Xbox Series X").images(Collections.singletonList(images[53])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(49799.0)).quantity(3).build(),
                    Product.builder().id(49L).name("Игра Elden Ring – Launch Edition (Xbox ONE, Xbox Series X)").images(Collections.singletonList(images[54])).categories(new HashSet<Category>() {{
                        add(televisions[2]);
					}}).price(BigDecimal.valueOf(4199.0)).quantity(3).build(),

            };
            productImageRepo.saveAll(Arrays.asList(images));
            categoryRepo.saveAll(Arrays.asList(categories));
            categoryRepo.saveAll(Arrays.asList(smartphones));
            categoryRepo.saveAll(Arrays.asList(addGoodsForSmartphones));
            categoryRepo.saveAll(Arrays.asList(audio));
            categoryRepo.saveAll(Arrays.asList(audioHeadphones));
            categoryRepo.saveAll(Arrays.asList(smartphonesHeadphones));
            categoryRepo.saveAll(Arrays.asList(televisions));
            productRepo.saveAll(Arrays.asList(products));
        };
    }
}
