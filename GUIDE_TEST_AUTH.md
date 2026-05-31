# Guide de Test - Authentification (Auth)

## Base URL
```
http://localhost:8080
```

## Endpoints disponibles

### 1. Enregistrement (Registration)
- **Endpoint:** `POST /api/auth/register`
- **Description:** Créer un nouveau compte utilisateur
- **Content-Type:** `application/json`

#### Requête d'exemple:
```json
{
  "nom": "Dupont",
  "prenom": "Jean",
  "email": "jean.dupont@example.com",
  "motDePasse": "Password123!",
  "role": "APPRENANT"
}
```

#### Réponse de succès (201/200):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWFuLmR1cG9udEBleeC5jb20iLCJpYXQiOjE2OTcxMjM0NTYsImV4cCI6MTY5NzIwOTg1Nn0.abc...",
  "type": "Bearer",
  "idUtilisateur": 1,
  "email": "jean.dupont@example.com",
  "nom": "Dupont",
  "prenom": "Jean",
  "role": "APPRENANT"
}
```

---

### 2. Connexion (Login)
- **Endpoint:** `POST /api/auth/login`
- **Description:** Se connecter avec ses identifiants
- **Content-Type:** `application/json`

#### Requête d'exemple:
```json
{
  "email": "jean.dupont@example.com",
  "motDePasse": "Password123!"
}
```

#### Réponse de succès (200):
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqZWFuLmR1cG9udEBleeC5jb20iLCJpYXQiOjE2OTcxMjM0NTYsImV4cCI6MTY5NzIwOTg1Nn0.abc...",
  "type": "Bearer",
  "idUtilisateur": 1,
  "email": "jean.dupont@example.com",
  "nom": "Dupont",
  "prenom": "Jean",
  "role": "APPRENANT"
}
```

---

## Scénario de Test Complet

### **Étape 1: Enregistrer un nouvel utilisateur APPRENANT**

**URL:** 
```
POST http://localhost:8080/api/auth/register
```

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "nom": "Martin",
  "prenom": "Pierre",
  "email": "pierre.martin@example.com",
  "motDePasse": "SecurePass@123",
  "role": "APPRENANT"
}
```

**ℹ️ Note:** Le matricule est généré automatiquement au format `APPR-{année}-{timestamp}-{aléatoire}`

**Résultat attendu:** 
- Status: `200 OK`
- Récupérer le `token` et l'`idUtilisateur` de la réponse
- Le matricule est visible dans la base de données

---

### **Étape 1b: Enregistrer un ENSEIGNANT**

**URL:** 
```
POST http://localhost:8080/api/auth/register
```

**Body (raw JSON):**
```json
{
  "nom": "Bernard",
  "prenom": "Sylvie",
  "email": "sylvie.bernard@example.com",
  "motDePasse": "TeachPass@2024",
  "role": "ENSEIGNANT"
}
```

**Résultat attendu:** 
- Status: `200 OK`

---

### **Étape 1c: Enregistrer un ADMINISTRATEUR**

**URL:** 
```
POST http://localhost:8080/api/auth/register
```

**Body (raw JSON):**
```json
{
  "nom": "Admin",
  "prenom": "Root",
  "email": "admin@example.com",
  "motDePasse": "AdminPass@2024",
  "role": "ADMINISTRATEUR"
}
```

**Résultat attendu:** 
- Status: `200 OK`

---

### **Étape 2: Se connecter avec les identifiants créés**

**URL:**
```
POST http://localhost:8080/api/auth/login
```

**Headers:**
```
Content-Type: application/json
```

**Body (raw JSON):**
```json
{
  "email": "pierre.martin@example.com",
  "motDePasse": "SecurePass@123"
}
```

**Résultat attendu:**
- Status: `200 OK`
- Récupérer le `token` de la réponse (JWT Bearer Token)

---

### **Étape 3: Utiliser le token pour accéder à des endpoints protégés**

Pour utiliser le token reçu lors de la connexion, l'ajouter dans le header `Authorization`:

**Format du Header:**
```
Authorization: Bearer {token}
```

**Exemple complet:**
```
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaWVycmUubWFydGluQGV4YW1wbGUuY29tIiwiaWF0IjoxNjk3MTIzNDU2LCJleHAiOjE2OTcyMDk4NTZ9.abc...
```

---

## Valeurs possibles pour les rôles

```
ADMINISTRATEUR
ENSEIGNANT
APPRENANT
ENTREPRISE
```

---

## Erreurs courantes et solutions

| Erreur | Cause | Solution |
|--------|-------|----------|
| `400 Bad Request` | Données invalides | Vérifier le format JSON et les validations |
| `409 Conflict` | Email déjà existant | Utiliser un email différent |
| `401 Unauthorized` | Identifiants incorrects | Vérifier l'email et le mot de passe |
| `500 Internal Server Error` | Erreur serveur | Vérifier les logs de l'application |

---

## Test avec Swagger UI

L'application expose Swagger UI à:
```
http://localhost:8080/swagger-ui.html
```

### Démarche:
1. Ouvrir `http://localhost:8080/swagger-ui.html`
2. Naviguer vers la section "Auth Controller"
3. Cliquer sur "Try it out" pour `/api/auth/register`
4. Entrer les données de test
5. Cliquer sur "Execute"
6. Copier le `token` reçu
7. En haut à droite de Swagger, cliquer sur "Authorize"
8. Entrer: `Bearer {token}`
9. Cliquer sur "Authorize" et fermer
10. Vous pouvez maintenant accéder aux endpoints protégés

---

## Test avec Postman

### Setup initial:

1. **Créer une nouvelle Collection** appelée "SIGL Auth Tests"

2. **Configurer les variables d'environnement:**
   - Créer une variable `base_url` = `http://localhost:8080`
   - Créer une variable `token` (vide initialement)

3. **Request 1 - Register:**
   - Méthode: `POST`
   - URL: `{{base_url}}/api/auth/register`
   - Body (JSON):
     ```json
     {
       "nom": "Lemaire",
       "prenom": "Sophie",
       "email": "sophie.lemaire@example.com",
       "motDePasse": "TestPass@2024",
       "role": "ENSEIGNANT"
     }
     ```
   - Tests (Scripts):
     ```javascript
     if (pm.response.code === 200) {
       pm.environment.set("token", pm.response.json().token);
       pm.environment.set("idUtilisateur", pm.response.json().idUtilisateur);
       console.log("Token sauvegardé: " + pm.response.json().token);
     }
     ```

4. **Request 2 - Login:**
   - Méthode: `POST`
   - URL: `{{base_url}}/api/auth/login`
   - Body (JSON):
     ```json
     {
       "email": "sophie.lemaire@example.com",
       "motDePasse": "TestPass@2024"
     }
     ```
   - Tests (Scripts):
     ```javascript
     if (pm.response.code === 200) {
       pm.environment.set("token", pm.response.json().token);
       console.log("Connexion réussie!");
     }
     ```

5. **Utiliser le token dans d'autres requêtes:**
   - Header: `Authorization: Bearer {{token}}`

