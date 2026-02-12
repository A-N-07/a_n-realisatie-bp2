# BDSM - Book Digital System Manager

**BDSM** (Book Digital System Manager) is een desktopapplicatie met een gebruiksvriendelijke GUI waarmee je overzicht houdt over je digitale boeken, zonder dat je deze hoeft te openen of te verplaatsen. De applicatie is gebouwd met JavaFX en draait lokaal op je computer – zonder internetverbinding.

---

## 📚 Functionaliteiten

- Voeg zelf boeken toe aan je digitale collectie
- Bekijk alle boeken in een overzicht
- Geef aan welke boeken je favoriet zijn
- Markeer welk boek je aan het lezen bent
- Bewaar de bladzijde waar je bent gebleven
- Geef zelf aan waar het boekbestand zich bevindt in je mappenstructuur
- Filter op favorieten of huidige leesstatus

---

## Vereisten

- Besturingssysteem: Windows, macOS of Linux
- **Java 17 of hoger**
- Een computer met digitale boeken (PDF, EPUB, etc.)
- Geen internetverbinding vereist

---

## Installatie

1. Zorg dat je Java (versie 17 of hoger) op je systeem hebt geïnstalleerd.  
   ➤ [Download Java hier](https://www.java.com/en/download/manual.jsp)

2. Ga naar de [XAMP downloadpage](https://www.apachefriends.org/download.html) en download de laatste versie.

3. Start XAMP en druk op 'start' Apache -> druk op 'start' MySql (in die volgorde) -> druk op 'Admin' van de MySQL module

4. Als het goed is zit je nu in PhpMyAdmin en kan je links boven op "new" drukken en maak je eigen database aan :)

5. Druk op de database die je hebt aangemaakt en klik op import, upload het bestand bdsm_db.sql

6. Zorg dat je deze repo gecloned hebt dat de [Intellij IDE](https://www.jetbrains.com/idea/download/?section=windows) gedownload hebt -> open de Ide en druk op het groene play button in de bovenbalk van de IDE

## Projectstatus

De applicatie bevindt zich momenteel in actieve ontwikkeling en wordt lokaal gebruikt door de developer voor testing en verdere uitbreiding. Het doel is om de applicatie gereed te maken voor een productieomgeving waarin eindgebruikers het systeem zelfstandig kunnen downloaden en gebruiken.

Om deze eerste release mogelijk te maken, worden de volgende verbeteringen nog geïmplementeerd:

* Edit-functionaliteit voor bestaande boeken
* Verbeterde responsiveness binnen de applicatie
* Een levendig en gebruiksvriendelijk UI-design
* Statistieken over de digitale boekencollectie
* Een knop om boeken direct te openen
* Een knop om de bestandslocatie in de file explorer te openen

Na implementatie van deze onderdelen zal de applicatie klaar zijn voor de eerste publieke release.

---

## Implementatieplan en Tijdlijn

De ontwikkeling van het systeem is gestart in januari 2025. Sindsdien is de applicatie stapsgewijs uitgebreid.

**Belangrijke mijlpalen:**

* **Januari 2025** — Start project en implementatie van de boekenlijst
* **December 2025** — Toevoegen van core functionaliteiten
* **Februari 2026** — Delete-functionaliteit, boekcreatie en bugfixes
* **Februari 2026** — Uitvoeren van tests

De komende ontwikkelfase richt zich op het afronden van de geplande features zodat het systeem stabiel genoeg is voor productiegebruik.

---

## Communicatie, Evaluatie en Verificatie

Het project is zelfstandig ontwikkeld in opdracht van een opdrachtgever. De communicatie vond plaats via fysieke afspraken waarin de voortgang werd besproken en feedback werd gegeven.

De applicatie wordt continu geëvalueerd door middel van tests en bugfixes. Nieuwe functionaliteiten worden eerst lokaal gevalideerd voordat ze onderdeel worden van de release. Dit proces helpt om de stabiliteit en bruikbaarheid van het systeem te waarborgen.



