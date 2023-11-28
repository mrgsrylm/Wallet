package io.github.mrgsrylm.wallet.fixtures;

import io.github.mrgsrylm.wallet.dto.wallet.WalletRequest;
import io.github.mrgsrylm.wallet.dto.wallet.WalletResponse;
import io.github.mrgsrylm.wallet.fixtures.generator.WalletObjectGenerator;
import io.github.mrgsrylm.wallet.model.WalletModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class GenerateWallet {
    public static WalletModel build() {
        WalletObjectGenerator generator = new WalletObjectGenerator();
        generator.generateWallet(GenerateUser.buildUser());

        return generator.generateWallet(GenerateUser.buildUser());
    }

    public static List<WalletModel> buildListWallet() {
        WalletModel walletModel1 = build();
        WalletModel walletModel2 = build();

        List<WalletModel> walletModels = new ArrayList<>();
        walletModels.add(walletModel1);
        walletModels.add(walletModel2);

        return walletModels;
    }

    public static Page<WalletModel> buildPageWallet() {
        List<WalletModel> data = buildListWallet();
        int pageNumber = 0;
        int pageSize = 10;

        int start = (pageNumber * pageSize);
        int end = Math.min(start + pageSize, data.size());
        List<WalletModel> pageContent = data.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(pageNumber, pageSize), data.size());
    }

    public static WalletRequest buildWalletRequest() {
        WalletModel rec = build();

        return WalletRequest.builder()
                .name(rec.getName())
                .iban(rec.getIban())
                .balance(rec.getBalance())
                .userId(rec.getId())
                .build();
    }

    public static WalletResponse buildWalletResponse() {
        WalletModel rec = build();

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
