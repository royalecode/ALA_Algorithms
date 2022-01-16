Artur Alcoverro - artur.alcoverro@students.salle.url.edu\
Edmon Bosch - edmon.bosch@students.salle.url.edu

# Arcade

## IDE i Llenguatge
IDE -> IntelliJ IDEA\
Llenguatge -> JAVA OpenJDK 17 Versio 17.0.1

## Llibreries
Arcade.jar\
Gson.jar

Perquè el codi funcioni es necessari tenir aquestes dos dependències incorporades en el projecte. Els fitxers .jar els trobareu dins de la mateixa carpeta del projecte, per afegir-los que siguin funcionals dins del IntelliJ heu d'accedir al menu\
File -> Project Structure -> Modules -> Dependencies\
Un cop allà donar-li al butó + per afegir una nova dependència del tipus JAR or Directories i seleccionar el .jar incorporat dins la carpeta, aquest procès dos cops un per cada jar diferent l'arcada i el gson.

## Funcionament de l'execució
1. Tenir la IDE IntelliJ IDEA amb el projecte obert i indexat.

2. Botó dret a la classe main i donar-li a l'opció Run 'Main'

3. Es mostrara un menú amb tres opcions diferents:\
    - Executar algorismes: Opció simple que executa simplement els algorismes escollits per cada minijoc en el següent menu, mostrant per pantalla la renderització de la solució trobada per cada joc. I en la terminal de la IDE veureu el temps amb milisegons que ha tardat l'algorisme en completar el minijoc.

    - Visualitzar algorismes: Opció molt semblant a l'anterior però amb la diferència que en aquest cas la renderització es dinàmica i es pot veure el procès que segueix l'algorisme per trobar la solució pintant el laberint i la sopa i així poder veure com recorrer l'algorisme pel mapa buscant la millor solució.

    - Executar anàlisi: Aquesta opció l'hem fet servir nosaltres per el desenvolupament de la pràctica en l'apartat d'anàlisis dels resultats. Aquesta opció no renderitza les solucions amb interfície gràfica, simplement executa els algoritmes escollits en el següent menu en bucle des de dimensions 5x5 fins a 199x199 i mostra per pantalla de terminal els temps d'execució que tarden els algorismes en resoldre el minijoc. El mateix procès el fa en 5 cicles diferents i un cop acaba tot el anàlisis s'escriuen els resultats en fitxers en format json que llavors ens han permès fer les gràfiques per comparar els temps que tarden els algorismes i comparar-ho amb diferents màquines com un processador Intel i l'altre un M1 d'Apple.

4. Un cop escollit una de les opcions anteriors es mostra un menu per escollir amb quin algorisme resoldre el minijoc del laberint\
    - Backtracking
    - Backtracking amb millores
    - Branch and Bound

5. Un cop escollit una de les opcions anteriors es mostra un menu per escollir amb quin algorisme resoldre el minijoc de la sopa de lletres\
    - Greedy
    - Backtracking

En cas que volgueu provar laberints o sopes de lletres de diferents dimensions, en la classe Main al principi de tot trobareu 5 constants que representen les columnes i files per laberint, i també hi ha les dues constants per la sopa de lletres, a més a més de la constant per definir la seed que considereu.

