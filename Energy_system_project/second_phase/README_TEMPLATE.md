# Proiect Energy System Etapa 2

## Despre

Proiectare Orientata pe Obiecte, Seriile CA, CD
2020-2021

<https://ocw.cs.pub.ro/courses/poo-ca-cd/teme/proiect/etapa2>

Student: Sandu Stefania-Cristina, 324CA

## Rulare teste

Clasa Test#main
  * ruleaza solutia pe testele din checker/, comparand rezultatele cu cele de referinta
  * ruleaza checkstyle

Detalii despre teste: checker/README

Biblioteci necesare pentru implementare:
* Jackson Core 
* Jackson Databind 
* Jackson Annotations

Tutorial Jackson JSON: 
<https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson>

## Implementare

### Entitati

Proiectul contine pachetele input si output pentru citirea si afisarea datelor,
inclusiv clasele cu care se lucreaza, un pachet utils ce contine clase 
auxiliare, pachetul strategies si clasele Main si Test.

* pachetul input prezinta o interfata comuna EntityInput pentru
consumer si distributor

	* ConsumerInput
		* prezinta campurile specifice atributelor unui consumator,fiind
		  adaugate campuri auxiliare  pentru a completa informatiile despre un
	      consumator, cum ar fi daca acesta are penalizare, contractul si
	      distribuitorul actual, dar si posibilul contract si distribuitor nou,
	      pentru cazul in care acesta doreste sa-l schimbe
		* metoda 'totalBudget()' calculeaza bugetul lunar al unui consumator

	* DistributorInput
		* un distributor retine, pe langa campurile din input, si numarul de
		  clienti, pretul contractului actual pe care il ofera calculat in
		  fiecare luna, o lista cu consumatorii care au contract cu acesta, o
		  lista de contracte ale consumatorilor si o lista cu consumatorii care
		  au penalizare
		* clasa implementeaza interfata Observer, fiecare distribuitor urmarind
		  producatorii alesi

	* ProducerInput
		* clasa implementeaza interfata Observable
		* cand un producator este updated, isi reseteaza cantitatea de energie
		  si notifica astfel toti distribuitorii din 'prodDistributors' ca
		  trebuie sa isi caute alt producator, marcand astfel campul
		  'toChangeProducer' prin 'true'

* pachetul utils prezinta clasele Contract, care serveste drept clasa de input
  si output, continand exact id-ul unui consumator, pretul contractului si
  lunile ramase de plata, si EnergySystem, care serveste ca un intermediar
  intre input si output, metodele utilizate in simulare fiind implementate aici

	* EnergySystems este o clasa de tip Singleton, ce se comporta ca o baza de
	  date pentru un sistem energetic, accesul la ea fiind realizat prin metoda
	  statica getInstance

* pachetul output contine clasele generate prin factory, care implemnteaza
  aceeasi interfata, EntityOutput

* Main
	* se parseaza fisierele de input si apoi sunt copiate datele in liste de consumatori, distribuitori si monthly updates.
	* are loc simularea propriu-zisa
	* la sfarsit se genereaza clase de output care sa corespunda cerintelor pentru entitati utilizand design pattern-ul factory si se scrie in fisiere

### Flow

Simularea se desfasoara pentru fiecare din cele numberOfTurns luni cu ajutorul
metodei 'simulate()' din clasa EnergySystem astfel:

	* au loc actiunile din prima luna
	* pentru lunile implicite se actualizeaza costurile si se adauga noi
	  clienti daca este cazul
	* pentru fiecare distribuitor, daca acesta nu este bankrupt, se
	  calculeaza pretul actual al unui  contract, apoi se filtreaza
	  distribuitorul cu oferta minima dintre acestia
	* pentru fiecare consumator, se alege un contract, apoi acestia platesc
	* se aplica costurile pentru fiecare distribuitor si se recalculeaza
	  bugetele
	* au loc updates pentru producatori
	* pentru fiecare distribuitor in ordine se verifica daca acesta trebuie
	  sa-si caute alt producator, iar daca da, au loc modificarile
	  corespunzatoare si i se asigneaza un alt producator


### Design patterns

Design pattern-urile folosite sunt Singleton, Factory, Strategy si Observer.

Singleton apare in implementarea clasei EnergySystem, cum a fost mentionat
anterior.

Factory-ul genereaza aici clasele de output pentru consumer, distributor
si producer.

Strategy este folosit pentru alegerea startegiei (green, price, quantity),
in vederea obtinerii unei liste sortate pentru alegerea producatorilor.

Observer vizeaza clasele distribuitorilor si producatorilor astfel:

	* ProducerInput implementeaza interfata Observable, fiind subiectul
	  urmarit, iar daca au loc schimbari, acesta trebuie sa-si notifice
	  observatorii, adica distribuitorii
	* DistributorInput implementeaza interfata Observer, fiind
	  observatorul care in functie de campul 'toChangeProducer' trebuie
	  sa isi caute alt producator



## Link repo github
<https://github.com/stefaniasandu2812/proiect-etapa2-energy-system.git>

