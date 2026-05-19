package com.example.projet_sigl.service;

import com.example.projet_sigl.exception.BusinessException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

/**
 * Stockage des PDF de rapports sur le filesystem local.
 * Le chemin racine est paramétrable via `app.upload-dir` (par défaut `./uploads/rapports`).
 */
@Service
public class FileStorageService {

    private final Path root;

    public FileStorageService(@Value("${app.upload-dir:./uploads/rapports}") String dir) {
        this.root = Paths.get(dir).toAbsolutePath().normalize();
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new BusinessException("Impossible de créer le dossier d'upload : " + e.getMessage());
        }
    }

    /** Sauvegarde un fichier PDF et renvoie son nom unique stocké. */
    public String storePdf(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("Fichier vide");
        }
        String original = file.getOriginalFilename() == null ? "rapport.pdf" : file.getOriginalFilename();
        if (!original.toLowerCase().endsWith(".pdf")) {
            throw new BusinessException("Seuls les fichiers PDF sont acceptés");
        }
        String unique = UUID.randomUUID() + "_" + original.replaceAll("[^a-zA-Z0-9._-]", "_");
        Path target = root.resolve(unique).normalize();
        if (!target.startsWith(root)) {
            throw new BusinessException("Chemin de fichier invalide");
        }
        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new BusinessException("Erreur lors de l'écriture du fichier : " + e.getMessage());
        }
        return unique;
    }

    /** Récupère un fichier comme ressource Spring pour téléchargement. */
    public Resource loadAsResource(String filename) {
        try {
            Path file = root.resolve(filename).normalize();
            Resource res = new UrlResource(file.toUri());
            if (!res.exists() || !res.isReadable()) {
                throw new BusinessException("Fichier introuvable : " + filename);
            }
            return res;
        } catch (Exception e) {
            throw new BusinessException("Erreur d'accès au fichier : " + e.getMessage());
        }
    }

    public void delete(String filename) {
        try {
            Files.deleteIfExists(root.resolve(filename).normalize());
        } catch (IOException ignored) {
            // best effort
        }
    }
}
