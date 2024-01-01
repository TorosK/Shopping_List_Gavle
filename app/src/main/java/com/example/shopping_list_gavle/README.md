Högskolan i Gävle
Introduktion till att skapa appar för android
HT 2023
Projektnamn (appnamn):
Shopping List / Inköpslista

Toros Kutlu
Student ID: 23toku01
toros_kutlu@hotmail.com
Inledning
Beskriv din app-idé – vad appen ska kunna göra, vilken genre den kan anses tillhöra, varför du tycker att den behövs, varför du tycker att det är intressant, hur du kom fram till idén osv.
Ca ½ sida
Min appidé är en nyttoapp för inköpslistor, designad för att hjälpa användare att organisera sina shoppingaktiviteter på ett effektivt sätt. Denna app tillhör genren av personlig produktivitet och vardagsplanering. Den primära funktionen är att användaren kan skapa och hantera inköpslistor, lägga till varor tillhörande specifika kategorier, och markera varor som köpta eller ta bort dem. I en tid där digitalisering spelar en allt större roll i vardagen, är behovet av en sådan app tydligt. Den tillgodoser behovet av att ha en centraliserad och lättillgänglig plattform för att hantera inköp, vilket minskar risken för att glömma eller köpa dubbletter av varor.
Idén till appen kom från personliga erfarenheter av att hantera inköpslistor på papper, vilket ofta ledde till att listor blev bortglömda eller otillräckliga. Det var även problematiskt att redigera om man inte hade med sin penna. Ur ett arkiveringsperspektiv, för sökbarhet och för att hålla koll på shoppinghistoria med tillhörande datum finns det även fördelar med att ha digitala versioner av inköpslistor. Genom att digitalisera denna process kan användare inte bara spara tid och undvika frustration men också bidra till en mer hållbar livsstil genom att minska användningen av papper och pennor.
Problemanalys
Analysera problem som behöver lösas för att få en fungerande app. Den behöver kanske realtidsdata – vad finns det för alternativ? Finns det någon webbtjänst som tillhandahåller data du behöver etc.
½ - 1 sida
I utvecklingen av "Shopping_List_Gavle" mötte jag olika utmaningar som krävde noggranna lösningar för att skapa en fungerande app.
Datahantering och Lagring
Ett primärt problem var att effektivt hantera och lagra användardata. Jag valde en lokal SQLite-databas, som hanteras av DatabaseHelper.kt, för att lagra information om varor. Detta val prioriterades för enkelhet och snabb åtkomst till data, vilket är viktigt för en smidig användarupplevelse.
Användargränssnitt och Interaktion
Utformningen av användargränssnittet var avgörande. Målet var att skapa ett intuitivt och lättanvänt gränssnitt, vilket återspeglas i activity_main.xml och item_view.xml. Anpassningen av ItemsAdapter.kt var central för att presentera och hantera varor på ett effektivt sätt.
Funktioner och Användarupplevelse
Att säkerställa att alla planerade funktioner var genomförbara var en viktig del av analysen. Funktioner som att kategorisera och lägga till varor samt hantera separata listor för borttagna och köpta varor utgjorde kärnan i appens funktionalitet.
Tekniska Utmaningar
Appen krävde också hantering av tekniska aspekter som olika skärmstorlekar och enhetsorienteringar. En omfattande förståelse för Kotlin och Android Studios funktioner var nödvändig för att hantera dessa utmaningar.
Sammanfattning
Denna problemanalys var en kritisk del i utvecklingsprocessen, där jag balanserade mellan användarnas behov, tidsramen för inlämning innan deadline och de tekniska möjligheterna inom Android-utveckling. Genom att noggrant överväga varje aspekt kunde jag skapa en lösning som uppfyllde grundläggande krav och erbjöd en användarvänlig upplevelse.

UI
Redogör för dina funderingar kring appens layout och UI, dvs. dina val både när det gäller look-and-feel och användarens interaktion med appen. Ha gärna med några bilder.
Ca 1 sida

Tidig version

En tidig tråkig simpel grå torr nedbantad version av appen, där funktionalitet var högre fokus och prioritet än design. 	”Final Boss”
Efter mer fokus på design och användarvänlighet. Men man upplever nästan aldrig att man är 100% ”färdig”. Finns alltid mer optimering att uppnå.

Användargränssnittet i appen "Shopping_List_Gavle" är designat med fokus på enkelhet och effektivitet, vilket återspeglas i dess layout och interaktionsdesign. Gränssnittet är strukturerat för att ge användaren en omedelbar överblick och en intuitiv navigering.
När appen startas, presenteras användaren med huvudskärmen som innehåller de primära elementen för interaktion. I activity_main.xml, är gränssnittet uppbyggt som en LinearLayout, vilket innebär att elementen är ordnade vertikalt. Denna design väljer enkelhet och direkt tillgång till appens funktioner.
•	Textinmatningsfält (EditText): Placerad överst på skärmen, där användare kan skriva in namnet på den vara de vill lägga till. Denna komponent är lättillgänglig och tydlig för användaren.
•	Dropdown-lista (Spinner): Direkt under textinmatningsfältet finns en dropdown-lista för att välja kategori för varje vara. Denna funktion möjliggör bättre framtida sortering, sökbarhet och organisation av varor.
•	Knappar för hantering av varor: Under dropdown-listan finns en knapp som tillåter användare att lägga till varor i listan, med tillhörande Toast-meddelanden och felhantering för att hantera tomma namn. Under listorna finns två knappar för att visa borttagna och köpta varor. Dessa är klart separerade och lättförståeliga, vilket bidrar till en effektiv användarupplevelse. Knapparna har färg-gradient (drawable/button_gradient.xml), border, shadow och förändras visuellt när de trycks/väljs.
I MainActivity.kt, används en RecyclerView för att visa listan över varor. Detta är ett effektivt sätt att hantera dynamiska listor och ger en smidig scrollupplevelse. Varje vara visas i en anpassad vy som definieras i item_view.xml, bestående av tre TextViews för att visa namn, kategori och datum/tid. Denna layout ger en ren och organiserad presentation av varje vara.
Interaktion med Listan: Listan går att ’scrolla’. Varor i listan är interaktiva. Användaren kan trycka på en vara. En AlertDialog ruta/fönster visas för att antingen markera den klickade/valda varan som köpt eller ta bort den, vilket hanteras i ItemsAdapter.kt. Denna funktionalitet är integrerad på ett sådant sätt att det är intuitivt för användaren att interagera med varje objekt i listan.
Varje lista (standard, borttagna, köpta varor) har en unik bakgrundsfärg, vilket är definierat i appens teman och resurser. Denna användning av färg hjälper till att visuellt skilja olika listtyper och förbättrar användarens förmåga att snabbt navigera i appen. Notera att appen inte är optimerad för färgblinda/handikappade, men detta är ett möjligt framtida utvecklingsområde och kräver mer expertis, research och resurser.
Användargränssnittet i "Shopping_List_Gavle" är utformat för att vara intuitivt och effektivt, med en tydlig struktur som gör det enkelt för användare att lägga till, organisera och hantera sina inköpslistor. Den enkla layouten kompletteras med en väl genomtänkt användarinteraktion, vilket gör appen lättanvänd och tillgänglig för ett brett spektrum av användare.
Implementation
Beskriv din lösning: vilka designval du gjort, vilka alternativa lösningar (om några) är möjliga och varför du valde att göra på det sättet du gjorde, vilka features som du tänkt ha från början finns med i den färdiga appen och vilka som du eventuellt fick avstå från etc. Redogör för arbetsprocessen. Kommentera dellösningar som du hade mest problem med, eller som du helt enkelt tycker är intressanta.
1-2 sidor

I utvecklingsprocessen av "Shopping_List_Gavle" har jag använt mig av Kotlin som programmeringsspråk och Android Studio som utvecklingsmiljö. Min lösning baserar sig på en tydlig struktur och organisation av kod och filer, vilket är avgörande för att uppnå en stabil och väl fungerande app. Jag bestämde mig för att primärt använda Engelska i källkoden vilket är tradition inom mjukvaruutveckling och standard inom Tech branschen.

Designval och Arkitektur

Appen är strukturerad i enlighet med vanliga Android-konventioner. Jag använde mig av flera Kotlin/XML-filer för att separera olika funktioner och ansvarsområden i appen:

•	MainActivity.kt: Här hanteras huvudaktiviteten i appen. Den innehåller logik för att interagera med användargränssnittet, som att lägga till varor, navigera mellan olika listvyer (standard, borttagna, köpta varor) och hantera användarens interaktioner.

•	DatabaseHelper.kt: En central komponent som hanterar all dataåtkomst och manipulation. Denna klass använder SQLite för att skapa och hantera tabeller för olika typer av varor, och för att utföra CRUD (Create, Read, Update, Delete) operationer.

•	Item.kt, DeletedItem.kt, PurchasedItem.kt: Dessa dataklasser representerar olika typer av varor i appen och deras struktur. De är enkla och tydliga, vilket underlättar databasoperationer och gör koden mer läsbar.

•	ItemsAdapter.kt: Ansvarar för att hantera hur varor visas i olika listvyer. Detta inkluderar logik för att binda data till vyer och hantera användarinteraktioner med listelementen.
I layout-filerna activity_main.xml och item_view.xml definieras användargränssnittets struktur och utseende. Dessa filer använder sig av XML för att definiera positioner och egenskaper för olika UI-komponenter. Alla String resurser har segregerats till strings.xml för bättre tillgänglighet/’accessibility’ för uppdateringar och enklare översättning till andra språk.
Alternativa Lösningar
Ett alternativ till den valda databasstrukturen kunde ha varit att använda en enda tabell med en statuskolumn för att indikera om en vara är köpt, borttagen eller aktiv. Jag valde dock att separera dem i olika tabeller för att göra databasoperationer mer överskådliga och för att undvika komplexa frågor.
Features och Arbetsprocess
Från början var målet att skapa en app där användare kunde lägga till, visa och ta bort varor från sin inköpslista. Dessa grundläggande funktioner finns med i den färdiga appen. Utöver det har jag lagt till möjligheten att kategorisera varor och att hantera separata listor för borttagna och köpta varor.
En stor del av arbetet bestod i att säkerställa en sömlös och intuitiv användarupplevelse. Detta innebar att noggrant designa användargränssnittet och att finjustera interaktionen mellan UI-komponenter och databasoperationer.
Utmaningar och Intressanta Lösningar
En av de större utmaningarna var att hantera databasoperationer effektivt och att synkronisera dessa med användargränssnittet. Särskilt utmanande var det att implementera logiken för att flytta varor mellan olika listor och att uppdatera vyer i realtid när en vara läggs till, tas bort eller flyttas. Lösningen på detta var att noggrant hantera databasåtkomst och att använda RecyclerView tillsammans med anpassade adapterklasser för att dynamiskt uppdatera listor baserat på användarens handlingar.
Sammanfattningsvis har utvecklingen av "Shopping_List_Gavle" varit en process som inneburit att noggrant balansera mellan användarvänlighet, funktionalitet och teknisk genomförbarhet. Genom att fokusera på en välorganiserad kodstruktur och en användarcentrerad design, har jag kunnat skapa en app som är både praktisk och lätt att använda.
Jag har identifierat brister i det nuvarande programmet. Hade jag haft mer tid och resurser så hade jag byggt ut ännu mer funktionalitet och gjort det ännu mer användarvänligt. Saker som saknas:

•	Sökbarhet.
•	Sortering av varor efter kategori, datum, alfabetisk ordning.
•	Redigering av varor (att kunna ändra namn, kategori, datum för existerande varor).
•	Återställning av borttagna och köpta varor till shoppinglistan (att flytta ’items’ från ’deleted items list’ och ’purchased item list’ till ’default shopping items list’).
•	Överväg att använda molntjänster för databas och för att kunna skapa konto för att enkelt kunna återställa sina inköpslistor på andra enheter med samma konto (annars existerar all data enbart lokalt).
•	Test-miljö för att optimera bl.a. skalbarhet och robusthet.
•	Mer förklarande kommentarer i koden och mer detaljerad kommentering.
•	Optimering av kod för skalbarhet, hastighet/prestanda, säkerhet och personlig integritet/’privacy’.
•	Uppstädning av kod (undvik repetitiv kod och städa bort kod som inte används längre).
Slutsats
Reflektera över ditt projektarbete. Har du valt rätt med avseende på svårighetsgrad? Hade du från början den kunskap som krävdes för att slutföra projektet? Vad har du lärt dig under arbetets gång? Osv.
½ - 1 sida

Projektet "Shopping_List_Gavle" har varit en utmanande och givande upplevelse inom apputveckling, passande för min nuvarande kunskapsnivå i Android-utveckling och Kotlin. Jag började med grundläggande kunskaper men behövde fördjupa mig i många områden, vilket ledde till en betydande kunskapstillväxt.

Lärdomar och Utmaningar
Projektets genomförande gav värdefulla insikter i flera nyckelaspekter:

•	Databashantering: Jag lärde mig hantera en lokal databas, vilket innefattade skapandet och hanteringen av tabeller samt synkronisering med användargränssnittet.

•	Användargränssnittsdesign: Utformningen av ett responsivt och användarvänligt gränssnitt var en viktig del av projektet.

•	Kodstruktur: Att hålla koden välorganiserad var avgörande, särskilt när projektet växte i omfattning.

Att balansera mellan databasoperationer och användargränssnittsinteraktioner var en av de största utmaningarna. Det krävdes noggrann planering och testning för att säkerställa en sömlös samverkan mellan appens olika delar.

Design och användargränssnitt (UI) inom mjukvaruutveckling är ett fascinerande ämne som väcker den inre konstnären och arkitekten. Hanteringen av XML-filer är mer likt ett pussel och skiljer sig från övrig programmering som oftast är mer logiskt orienterad.

Det är intressant att se hur teoretiska idéer går från planeringsbordet till praktiken i kod och hur programmet byggs upp bit för bit. Även de hinder och utmaningar man stöter på som leder till omplanering och kompromisser, gör att man slipar på sina problemlösningsförmågor.

Projektet har varit en omfattande läroprocess som har utvecklat både mina tekniska färdigheter och min förståelse för apputvecklingsprocessen. Jag har lärt mig vikten av att planera och strukturera en app noggrant, och hur varje del bidrar till helheten och användarupplevelsen. Denna erfarenhet har varit ovärderlig och har förberett mig för framtida utmaningar inom apputveckling.
