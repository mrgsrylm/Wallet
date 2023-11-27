package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.user.UserResponse;
import io.github.mrgsrylm.wallet.dto.wallet.WalletRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.WalletObjectGenerator;
import io.github.mrgsrylm.wallet.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class GenerateWallet {
    public static Wallet build() {
        WalletObjectGenerator generator = new WalletObjectGenerator();
        generator.generateWallet(GenerateUser.build());

        return generator.generateWallet(GenerateUser.build());
    }

    public static List<Wallet> buildListWallet() {
        Wallet wallet1 = build();
        Wallet wallet2 = build();

        List<Wallet> wallets = new ArrayList<>();
        wallets.add(wallet1);
        wallets.add(wallet2);

        return wallets;
    }

    public static Page<Wallet> buildPageWallet() {
        List<Wallet> data = buildListWallet();
        int pageNumber = 0;
        int pageSize = 10;

        int start = (pageNumber * pageSize);
        int end = Math.min(start + pageSize, data.size());
        List<Wallet> pageContent = data.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), data.size());
    }

    public static WalletRequest buildWalletRequest() {
        Wallet rec = build();

        return WalletRequest.builder()
                .name(rec.getName())
                .iban(rec.getIban())
                .balance(rec.getBalance())
                .userId(rec.getId())
                .build();
    }

    public static WalletResponse buildWalletResponse() {
        Wallet rec = build();

        return WalletResponse.builder()
                .id(rec.getId())
                .name(rec.getName())
                .iban(rec.getIban())
                .balance(rec.getBalance())
                .user(GenerateUser.buildUserResponse()).build();
    }


    public static List<WalletResponse> buildListWalletResponse() {
        WalletResponse wallet1 = buildWalletResponse();
        WalletResponse wallet2 = buildWalletResponse();

        List<WalletResponse> wallets = new ArrayList<>();
        wallets.add(wallet1);
        wallets.add(wallet2);

        return wallets;
    }

    public static Page<WalletResponse> buildPageWalletResponse() {
        List<WalletResponse> walletsResponse = buildListWalletResponse();
        int pageNumber = 0;
        int pageSize = 10;

        int start = (pageNumber * pageSize);
        int end = Math.min(start + pageSize, walletsResponse.size());
        List<WalletResponse> pageContent = walletsResponse.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), walletsResponse.size());
    }
}
