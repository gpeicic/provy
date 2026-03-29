## PROVY
Backend za platformu koja povezuje pružatelje uslužnih djelatnosti 
(frizerski saloni, kozmetički saloni i sl.) s korisnicima koji žele 
rezervirati termin online.

## Što radi

- Registracija i autentikacija korisnika i pružatelja usluga (JWT)
- Upravljanje profilima pružatelja usluga i njihovom ponudom
- Rezervacija termina s notifikacijama
- Recenzije nakon obavljene usluge
- Pretraga pružatelja po lokaciji (Haversine formula + geocoding)
- Rate limiting za zaštitu API-ja
- Role-based access control (admin, provider, user)

## Tech stack

- Java / Spring Boot
- JWT autentikacija
- PostgreSQL
- Geocoding integracija

## Pokretanje

1. Kloniraj repo
2. Postavi `application.properties` prema `application.properties.example`
3. Pokreni PostgreSQL i kreiraj bazu
4. `mvn spring-boot:run`

