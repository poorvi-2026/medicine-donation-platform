# Medicine Donation & Redistribution Platform

A Spring Boot REST API backend that connects households with unused, unexpired
medicines to verified NGOs and receivers — enabling safe, structured
redistribution through expiry validation, request matching, and
admin-moderated verification.

## Team
- Poorvi Parashar — Donor Module + Matching & Logistics Service
- Rafiya Parveen — NGO/Admin Module + Verification & Security

## Tech Stack
- Java 17+, Spring Boot, Spring Data JPA, Hibernate ORM
- MySQL
- Spring Security + JWT
- Maven
- Postman (API testing)

## Getting Started

1. Clone the repo:
   ```
   git clone https://github.com/poorvi-2026/medicine-donation-platform.git
   ```
2. In STS: `File → Import → Maven → Existing Maven Projects` → select the cloned folder
3. Copy `application-example.properties` → rename to `application.properties` →
   place it in `src/main/resources/` → fill in your own local MySQL credentials
   (this file is gitignored — never commit real credentials)
4. Create the database in MySQL Workbench:
   ```sql
   CREATE DATABASE medishare_db;
   ```
5. Run the main application class → test endpoints in Postman

## Branching Strategy

`main` is always kept stable and deployable. Nobody commits directly to `main`.

| Branch | Owner | Scope |
|---|---|---|
| `donor-module` | Poorvi | Donor entity, listing, expiry validation, matching |
| `ngo-admin-module` | Rafiya | NGO entity, admin approval workflow, security/JWT |

**Daily workflow:**
```
git checkout <your-branch>
git pull origin main          # get latest shared changes first
... write code ...
git add .
git commit -m "clear, specific message"
git push origin <your-branch>
```

Then open a **Pull Request** on GitHub into `main` — the other person reviews
and merges. Never both edit `main` directly.

**Before starting each session:** always `git pull` first, especially on
shared files like `pom.xml` or any shared entity/enum (e.g. `Role.java`) —
these are the most common source of merge conflicts.

## Project Structure

```
com.dgi.medishare
├── entity/        # Donor, Medicine, Ngo, DonationRequest, Admin, Role
├── repository/     # JpaRepository interfaces
├── service/        # business logic (expiry validation, matching, approvals)
├── controller/     # REST endpoints
├── dto/            # request/response objects
├── security/        # JWT + Spring Security config
└── exception/      # global exception handling
```

## Future Scope
- Geolocation-based donor–NGO matching
- Logistics/pickup scheduling
- Notification system (email/SMS)
- Caching + pagination for scale
