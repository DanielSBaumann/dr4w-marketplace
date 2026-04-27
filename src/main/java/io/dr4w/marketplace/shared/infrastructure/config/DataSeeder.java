package io.dr4w.marketplace.shared.infrastructure.config;

import io.dr4w.marketplace.catalog.domain.model.Category;
import io.dr4w.marketplace.catalog.domain.port.in.CreateProductUseCase;
import io.dr4w.marketplace.identity.domain.port.in.RegisterUserUseCase;
import io.dr4w.marketplace.identity.domain.model.UserRole;
import io.dr4w.marketplace.identity.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements ApplicationRunner {

    private static final String VENDOR_EMAIL = "store@dr4w.io";
    private static final String BUYER_EMAIL  = "buyer@dr4w.io";

    private final UserRepository userRepository;
    private final RegisterUserUseCase registerUser;
    private final CreateProductUseCase createProduct;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (userRepository.existsByEmail(VENDOR_EMAIL)) {
            log.info("Seed data already present — skipping.");
            return;
        }

        log.info("Seeding demo data...");

        var vendor = registerUser.execute(new RegisterUserUseCase.Command(
                "DR4W Store", VENDOR_EMAIL, "Store@12345!", UserRole.VENDOR));
        registerUser.execute(new RegisterUserUseCase.Command(
                "Demo Buyer", BUYER_EMAIL, "Buyer@12345!", UserRole.BUYER));

        UUID vid = vendor.getId();
        seed(vid);

        log.info("Demo data seeded. Vendor: {} / Store@12345! | Buyer: {} / Buyer@12345!", VENDOR_EMAIL, BUYER_EMAIL);
    }

    private void seed(UUID vendorId) {
        // ELECTRONICS
        product(vendorId, "MacBook Pro 14\"", "Apple", "M3 chip, 18GB RAM, 512GB SSD. Exceptional performance for creative professionals.", Category.ELECTRONICS, "8999.99", 15, img("macbook"));
        product(vendorId, "Sony WH-1000XM5", "Sony", "Industry-leading noise canceling headphones with 30-hour battery and crystal-clear hands-free calling.", Category.ELECTRONICS, "1799.99", 30, img("headphones"));
        product(vendorId, "Samsung 55\" QLED 4K", "Samsung", "Neo QLED display with Quantum HDR 32x and Neural Quantum Processor.", Category.ELECTRONICS, "4299.99", 8, img("tv"));
        product(vendorId, "iPad Pro 12.9\"", "Apple", "M2 chip with Liquid Retina XDR display. The ultimate iPad experience.", Category.ELECTRONICS, "6499.99", 20, img("ipad"));

        // ACCESSORIES
        product(vendorId, "MagSafe Charger", "Apple", "15W wireless charger with perfect alignment for iPhone 12 and later.", Category.ACCESSORIES, "179.99", 50, img("charger"));
        product(vendorId, "USB-C Hub 7-in-1", "Anker", "HDMI 4K, 100W PD, USB 3.0 x2, SD card reader, Ethernet.", Category.ACCESSORIES, "249.99", 45, img("hub"));
        product(vendorId, "Apple Watch Band", "Apple", "Sport Loop band in Midnight, 41mm. Soft, breathable and lightweight.", Category.ACCESSORIES, "99.99", 60, img("watchband"));

        // FASHION
        product(vendorId, "Classic White Tee", "Uniqlo", "100% supima cotton, relaxed fit. The essential wardrobe staple.", Category.FASHION, "89.99", 100, img("tshirt"));
        product(vendorId, "Slim Chino Pants", "Gap", "Stretch twill fabric with a modern slim cut. Available in multiple colors.", Category.FASHION, "199.99", 40, img("pants"));
        product(vendorId, "Leather Sneakers", "Common Projects", "Italian leather with minimalist design. Achilles Low in white.", Category.FASHION, "899.99", 15, img("sneakers"));

        // SPORTS
        product(vendorId, "Yoga Mat Pro", "Lululemon", "5mm reversible yoga mat with natural rubber grip. 68\" x 24\".", Category.SPORTS, "349.99", 35, img("yogamat"));
        product(vendorId, "Adjustable Dumbbell Set", "Bowflex", "SelectTech 552. Adjusts from 5 to 52 lbs. Replaces 15 sets of weights.", Category.SPORTS, "2999.99", 10, img("dumbbell"));

        // KIDS
        product(vendorId, "LEGO Technic Set", "LEGO", "2-in-1 model: muscle car or dragster. 1461 pieces for ages 10+.", Category.KIDS, "499.99", 25, img("lego"));
        product(vendorId, "Drawing Tablet", "Wacom", "Intuos Small wireless drawing tablet for kids. No experience needed.", Category.KIDS, "399.99", 20, img("tablet"));

        // BOOKS
        product(vendorId, "Clean Code", "Robert C. Martin", "A handbook of agile software craftsmanship. Essential for every developer.", Category.BOOKS, "89.99", 50, img("cleancode"));
        product(vendorId, "System Design Interview", "Alex Xu", "Vol 1 & 2. An insider's guide to cracking the most feared interview.", Category.BOOKS, "129.99", 40, img("systemdesign"));
    }

    private void product(UUID vendorId, String name, String brand, String desc,
                         Category category, String price, int stock, String image) {
        createProduct.execute(new CreateProductUseCase.Command(
                vendorId, name, brand, desc, category,
                new BigDecimal(price), stock,
                image, null, null, null
        ));
    }

    private String img(String seed) {
        return "https://picsum.photos/seed/" + seed + "/600/600";
    }
}
