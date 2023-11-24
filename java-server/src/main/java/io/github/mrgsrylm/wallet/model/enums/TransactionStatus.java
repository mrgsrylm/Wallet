package io.github.mrgsrylm.wallet.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * PENDING: Transaksi sedang menunggu persetujuan atau konfirmasi.
 * SUCCESS: Transaksi berhasil dieksekusi tanpa masalah.
 * FAILED: Transaksi gagal karena beberapa alasan, seperti dana yang tidak mencukupi, masalah teknis, dll.
 * PROCESSING: Transaksi sedang dalam proses eksekusi.
 * REVERSED: Transaksi dibatalkan atau dibalikkan.
 * ON HOLD: Transaksi ditahan atau ditunda untuk sementara waktu.
 **/
@Getter
@AllArgsConstructor
public enum TransactionStatus {
    PENDING("PENDING"),
    SUCCESS("SUCCESS"),
    ERROR("FAILED"),
    PROCESSING("PROCESSING"),
    REVERSED("REVERSED"),
    ON_HOLD("ON HOLD");

    private String label;
}
