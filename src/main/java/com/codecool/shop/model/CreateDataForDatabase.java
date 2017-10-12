package com.codecool.shop.model;

import com.codecool.shop.dao.implementation.*;

public class CreateDataForDatabase {

    public static void createDaoMem() {

        //setting up a new supplier
        Supplier bkcCopy = new Supplier("Bkc-opy", "");
        Supplier magicEductation = new Supplier("Magic Education", "Corrupters");
        Supplier fastAndTheFurious = new Supplier("F&F", "Fast and the Furious");
        Supplier personalityMaker = new Supplier("Personality Maker", "We have enough money for everything");
        Supplier itc = new Supplier("ITC", "The biggest copy factory");
        SupplierDaoMem.getInstance().add(bkcCopy);
        SupplierDaoMem.getInstance().add(magicEductation);
        SupplierDaoMem.getInstance().add(fastAndTheFurious);
        SupplierDaoMem.getInstance().add(personalityMaker);
        SupplierDaoMem.getInstance().add(itc);

        //setting up a new product category
        ProductCategory idCard = new ProductCategory("Identity Card", "Identity Card", "It's looks like the original hungarian Identity card.");
        ProductCategory addressCard = new ProductCategory("Address Card", "Address Card", "This card contain official address");
        ProductCategory driverLicence = new ProductCategory("Driver Licence", "Driver Licence", "With this awesome card, you can driving in Hungary");
        ProductCategory erettsegi = new ProductCategory("Érettségi", "Document", "This document justifies, that you finished the High School ");
        ProductCategory okj = new ProductCategory("OKJ", "Document", "This document justifies, you are good enough in one profession");
        ProductCategory diploma = new ProductCategory("Diploma", "Document", "This document justifies, you are master in one profession");
        ProductCategory language = new ProductCategory("Language Exam", "Document", "This document justifies, you are good enough in a language");
        ProductCategory bkk = new ProductCategory("BKK", "Ticket", "With this ticket you can traveling in Budapest ");
        ProductCategory pack = new ProductCategory("Pack", "Document", "You can buy some document in pack, to be sure, that will be work well ");
        ProductCategory franchise = new ProductCategory("Franchaise", "Printer", "With this special Printer you can make your own document");
        ProductCategoryDaoMem.getInstance().add(idCard);
        ProductCategoryDaoMem.getInstance().add(addressCard);
        ProductCategoryDaoMem.getInstance().add(driverLicence);
        ProductCategoryDaoMem.getInstance().add(erettsegi);
        ProductCategoryDaoMem.getInstance().add(okj);
        ProductCategoryDaoMem.getInstance().add(diploma);
        ProductCategoryDaoMem.getInstance().add(language);
        ProductCategoryDaoMem.getInstance().add(bkk);
        ProductCategoryDaoMem.getInstance().add(pack);
        ProductCategoryDaoMem.getInstance().add(franchise);


        //setting up products and printing it
        Product product1 = (new Product("Identity Card (Womean)", 41, "USD", "If you are don't like yourself, than you can be anybody", idCard, personalityMaker, "/img/product_1.jpg"));
        Product product2 = (new Product("Identity Card (Man)", 42, "USD", "If you are ugly, or maybe just you can not leave your country, with new ID card you can do that ", idCard, personalityMaker, "/img/product_2.jpg"));
        Product product3 = (new Product("Address Card", 180, "USD", "You can live everywhere with this card", addressCard, personalityMaker, "/img/product_3.jpg"));
        Product product4 = (new Product("Address Card (Family pack)", 180, "USD", "This pack include 4 address card what you can shear with your family or friends", addressCard, personalityMaker, "/img/product_4.jpg"));
        Product product5 = (new Product("Driver Licence", 230, "USD", "It's just looks like driver licence, without category ", driverLicence, fastAndTheFurious, "/img/product_5.jpg"));
        Product product6 = (new Product("B category - Driver Licence ", 580, "USD", "With this driver licence you can use you slovakian car in the hungarian roads", driverLicence, fastAndTheFurious, "/img/product_6.jpg"));
        Product product7 = (new Product("C category - Driver Licence ", 580, "USD", "With this driver licence is much easier to take the refugees in the country, and you should not  take car for police ", driverLicence, fastAndTheFurious, "/img/product_7.jpg"));
        Product product8 = (new Product("Érettségi", 400, "USD", "With this amazing document much easier get a job at awesome McDonald's, TESCO, SPAR, or at your friends ", erettsegi, magicEductation, "/img/product_8.jpg"));
        Product product9 = (new Product("Idegenvezető - OKJ", 450, "USD", "If you have this document you can talk with tourists about nice building. They are going to pay for that  ", okj, magicEductation, "/img/product_9.jpg"));
        Product product10 = (new Product("Ingatlankezelő - OKJ", 430, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", okj, magicEductation, "/img/product_10.jpg"));
        Product product11 = (new Product("Diploma", 4500, "USD", "It's just a simple diploma without university, if you boss will searching for your name ate the university he will be know it's a fail diploma", diploma, magicEductation, "/img/product_11.jpg"));
        Product product12 = (new Product("Közgazdász", 6300, "USD", "Gazdaságtudományi Műszaki és Gazdasági-és Társadalomtudományi Kar Gazdálkodási - Diploma", diploma, magicEductation, "/img/product_12.jpg"));
        Product product13 = (new Product("Logopédia szakos tanár és pszichopedagógia szakos terapeauta - Diploma", 6250, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", diploma, magicEductation, "/img/product_13.jpg"));
        Product product14 = (new Product("Építészmérnök", 7200, "HUF", " Széchenyi István Egyetem Műszaki Tudományi Kar ", diploma, magicEductation, "/img/product_14.jpg"));
        Product product15 = (new Product("English - Language Exam", 200, "USD", "With this document you will be english language ninja (on the paper) ", language, magicEductation, "/img/product_15.jpg"));
        Product product16 = (new Product("Germany - Language Exam", 225, "USD", "With this document you will be germany language ninja (on the paper) ", language, magicEductation, "/img/product_16.jpg"));
        Product product17 = (new Product("French - Language Exam", 230, "USD", "With this document you will be french language ninja (on the paper) ", language, magicEductation, "/img/product_17.jpg"));
        Product product18 = (new Product("Spain - Language Exam", 230, "USD", "With this document you will be spain language ninja (on the paper) ", language, magicEductation, "/img/product_17.jpg"));
        Product product19 = (new Product("Digital ticket - 30 day", 10, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_19.jpg"));
        Product product20 = (new Product("Paper ticket - 30 day ", 15, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_20.jpg"));
        Product product21 = (new Product("Paper ticket - 1 way ", 1, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_21.jpg"));
        Product product22 = (new Product("Identity Card + Address Card ", 80, "USD", "Your identity will be more real ", pack, magicEductation, "/img/product_22.jpg"));
        Product product23 = (new Product("Printer + shipping to you home", 40000, "USD", "With this printer you will able to made your own busniess, but 40% of your proift will be ours ", franchise, itc, "/img/product_23.jpg"));
        Product product24 = (new Product("Printer + shipping to border of your country ", 35000, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", franchise, itc, "/img/product_24.jpg"));
        ProductDaoMem.getInstance().add(product1);
        ProductDaoMem.getInstance().add(product2);
        ProductDaoMem.getInstance().add(product3);
        ProductDaoMem.getInstance().add(product4);
        ProductDaoMem.getInstance().add(product5);
        ProductDaoMem.getInstance().add(product6);
        ProductDaoMem.getInstance().add(product7);
        ProductDaoMem.getInstance().add(product8);
        ProductDaoMem.getInstance().add(product9);
        ProductDaoMem.getInstance().add(product10);
        ProductDaoMem.getInstance().add(product11);
        ProductDaoMem.getInstance().add(product12);
        ProductDaoMem.getInstance().add(product13);
        ProductDaoMem.getInstance().add(product14);
        ProductDaoMem.getInstance().add(product15);
        ProductDaoMem.getInstance().add(product16);
        ProductDaoMem.getInstance().add(product17);
        ProductDaoMem.getInstance().add(product18);
        ProductDaoMem.getInstance().add(product19);
        ProductDaoMem.getInstance().add(product20);
        ProductDaoMem.getInstance().add(product21);
        ProductDaoMem.getInstance().add(product22);
        ProductDaoMem.getInstance().add(product23);
        ProductDaoMem.getInstance().add(product24);


        for (int i = 0; i < SupplierDaoMem.getInstance().getAll().size(); i++) {
            SupplierDaoMem.getInstance().getAll().get(i).setId(i+1);
        }
        for (int i = 0; i < ProductCategoryDaoMem.getInstance().getAll().size(); i++) {
            ProductCategoryDaoMem.getInstance().getAll().get(i).setId(i+1);
        }
        for (int i = 0; i < ProductDaoMem.getInstance().getAll().size(); i++) {
            ProductDaoMem.getInstance().getAll().get(i).setId(i+1);

        }



    }
    public static void createData() {


        //setting up a new supplier
        Supplier bkcCopy = new Supplier("Bkc-opy", "Digital content and services");
        Supplier magicEductation = new Supplier("Magic Education", "Corrupters");
        Supplier fastAndTheFurious = new Supplier("F&F", "The alcohol is bad, okeeeee?");
        Supplier personalityMaker = new Supplier("Personality Maker", "We have enough money for everything");
        Supplier itc = new Supplier("ITC", "The biggest copy factory");
        SupplierDaoJdbc.getInstance().add(bkcCopy);
        SupplierDaoJdbc.getInstance().add(magicEductation);
        SupplierDaoJdbc.getInstance().add(fastAndTheFurious);
        SupplierDaoJdbc.getInstance().add(personalityMaker);
        SupplierDaoJdbc.getInstance().add(itc);

        //setting up a new product category
        ProductCategory idCard = new ProductCategory("Identity Card", "Identity Card", "It's looks like the original hungarian Identity card.");
        ProductCategory addressCard = new ProductCategory("Address Card", "Address Card", "This card contain official address");
        ProductCategory driverLicence = new ProductCategory("Driver Licence", "Driver Licence", "With this awesome card, you can driving in Hungary");
        ProductCategory erettsegi = new ProductCategory("Érettségi", "Document", "This document justifies, that you finished the High School ");
        ProductCategory okj = new ProductCategory("OKJ", "Document", "This document justifies, you are good enough in one profession");
        ProductCategory diploma = new ProductCategory("Diploma", "Document", "This document justifies, you are master in one profession");
        ProductCategory language = new ProductCategory("Language Exam", "Document", "This document justifies, you are good enough in a language");
        ProductCategory bkk = new ProductCategory("BKK", "Ticket", "With this ticket you can traveling in Budapest ");
        ProductCategory pack = new ProductCategory("Pack", "Document", "You can buy some document in pack, to be sure, that will be work well ");
        ProductCategory franchise = new ProductCategory("Franchaise", "Printer", "With this special Printer you can make your own document");
        ProductCategoryDAOJdbc.getInstance().add(idCard);
        ProductCategoryDAOJdbc.getInstance().add(addressCard);
        ProductCategoryDAOJdbc.getInstance().add(driverLicence);
        ProductCategoryDAOJdbc.getInstance().add(erettsegi);
        ProductCategoryDAOJdbc.getInstance().add(okj);
        ProductCategoryDAOJdbc.getInstance().add(diploma);
        ProductCategoryDAOJdbc.getInstance().add(language);
        ProductCategoryDAOJdbc.getInstance().add(bkk);
        ProductCategoryDAOJdbc.getInstance().add(pack);
        ProductCategoryDAOJdbc.getInstance().add(franchise);


        //setting up products and printing it
        Product product1 = (new Product("Identity Card (Womean)", 41, "USD", "If you are don't like yourself, than you can be anybody", idCard, personalityMaker, "/img/product_1.jpg"));
        Product product2 = (new Product("Identity Card (Man)", 42, "USD", "If you are ugly, or maybe just you can not leave your country, with new ID card you can do that ", idCard, personalityMaker, "/img/product_2.jpg"));
        Product product3 = (new Product("Address Card", 180, "USD", "You can live everywhere with this card", addressCard, personalityMaker, "/img/product_3.jpg"));
        Product product4 = (new Product("Address Card (Family pack)", 180, "USD", "This pack include 4 address card what you can shear with your family or friends", addressCard, personalityMaker, "/img/product_4.jpg"));
        Product product5 = (new Product("Driver Licence", 230, "USD", "It's just looks like driver licence, without category ", driverLicence, fastAndTheFurious, "/img/product_5.jpg"));
        Product product6 = (new Product("B category - Driver Licence ", 580, "USD", "With this driver licence you can use you slovakian car in the hungarian roads", driverLicence, fastAndTheFurious, "/img/product_6.jpg"));
        Product product7 = (new Product("C category - Driver Licence ", 580, "USD", "With this driver licence is much easier to take the refugees in the country, and you should not  take car for police ", driverLicence, fastAndTheFurious, "/img/product_7.jpg"));
        Product product8 = (new Product("Érettségi", 400, "USD", "With this amazing document much easier get a job at awesome McDonald's, TESCO, SPAR, or at your friends ", erettsegi, magicEductation, "/img/product_8.jpg"));
        Product product9 = (new Product("Idegenvezető - OKJ", 450, "USD", "If you have this document you can talk with tourists about nice building. They are going to pay for that  ", okj, magicEductation, "/img/product_9.jpg"));
        Product product10 = (new Product("Ingatlankezelő - OKJ", 430, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", okj, magicEductation, "/img/product_10.jpg"));
        Product product11 = (new Product("Diploma", 4500, "USD", "It's just a simple diploma without university, if you boss will searching for your name ate the university he will be know it's a fail diploma", diploma, magicEductation, "/img/product_11.jpg"));
        Product product12 = (new Product("Közgazdász", 6300, "USD", "Gazdaságtudományi Műszaki és Gazdasági-és Társadalomtudományi Kar Gazdálkodási - Diploma", diploma, magicEductation, "/img/product_12.jpg"));
        Product product13 = (new Product("Logopédia szakos tanár és pszichopedagógia szakos terapeauta - Diploma", 6250, "USD", "If will be Ingatlankezelő, you will be the boss of many realtor ", diploma, magicEductation, "/img/product_13.jpg"));
        Product product14 = (new Product("Építészmérnök", 7200, "HUF", " Széchenyi István Egyetem Műszaki Tudományi Kar ", diploma, magicEductation, "/img/product_14.jpg"));
        Product product15 = (new Product("English - Language Exam", 200, "USD", "With this document you will be english language ninja (on the paper) ", language, magicEductation, "/img/product_15.jpg"));
        Product product16 = (new Product("Germany - Language Exam", 225, "USD", "With this document you will be germany language ninja (on the paper) ", language, magicEductation, "/img/product_16.jpg"));
        Product product17 = (new Product("French - Language Exam", 230, "USD", "With this document you will be french language ninja (on the paper) ", language, magicEductation, "/img/product_17.jpg"));
        Product product18 = (new Product("Spain - Language Exam", 230, "USD", "With this document you will be spain language ninja (on the paper) ", language, magicEductation, "/img/product_17.jpg"));
        Product product19 = (new Product("Digital ticket - 30 day", 10, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_19.jpg"));
        Product product20 = (new Product("Paper ticket - 30 day ", 15, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_20.jpg"));
        Product product21 = (new Product("Paper ticket - 1 way ", 1, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", bkk, bkcCopy, "/img/product_21.jpg"));
        Product product22 = (new Product("Identity Card + Address Card ", 80, "USD", "Your identity will be more real ", pack, magicEductation, "/img/product_22.jpg"));
        Product product23 = (new Product("Printer + shipping to you home", 40000, "USD", "With this printer you will able to made your own busniess, but 40% of your proift will be ours ", franchise, itc, "/img/product_23.jpg"));
        Product product24 = (new Product("Printer + shipping to border of your country ", 35000, "USD", "With this printer you will able to made your own busniess, but 20% of your proift will be ours", franchise, itc, "/img/product_24.jpg"));
        ProductDaoJdbc.getInstance().add(product1);
        ProductDaoJdbc.getInstance().add(product2);
        ProductDaoJdbc.getInstance().add(product3);
        ProductDaoJdbc.getInstance().add(product4);
        ProductDaoJdbc.getInstance().add(product5);
        ProductDaoJdbc.getInstance().add(product6);
        ProductDaoJdbc.getInstance().add(product7);
        ProductDaoJdbc.getInstance().add(product8);
        ProductDaoJdbc.getInstance().add(product9);
        ProductDaoJdbc.getInstance().add(product10);
        ProductDaoJdbc.getInstance().add(product11);
        ProductDaoJdbc.getInstance().add(product12);
        ProductDaoJdbc.getInstance().add(product13);
        ProductDaoJdbc.getInstance().add(product14);
        ProductDaoJdbc.getInstance().add(product15);
        ProductDaoJdbc.getInstance().add(product16);
        ProductDaoJdbc.getInstance().add(product17);
        ProductDaoJdbc.getInstance().add(product18);
        ProductDaoJdbc.getInstance().add(product19);
        ProductDaoJdbc.getInstance().add(product20);
        ProductDaoJdbc.getInstance().add(product21);
        ProductDaoJdbc.getInstance().add(product22);
        ProductDaoJdbc.getInstance().add(product23);
        ProductDaoJdbc.getInstance().add(product24);

    }
}
