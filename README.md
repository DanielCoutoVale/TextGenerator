# TextGenerator
An open-source text generator applying the Systemic Functional Theory of language.

This is a work in progress. The first beta version is planned for December 2019 and it will be announce here.

## How to use
```java
Generator generator = new Generator("[path-to-grammar-directory]");
generator.generate("[lex]", "[feature1]", "[feature2]"...);
```

## Issues

##### 1. Number

English:
- I ate one **pizza**.
- I ate two **pizzas**.

##### 2. Common Name

German:
- Ich habe **eine** Pizza gegessen.
- Ich habe **ein** Eis gegessen.

##### 3. Proper Name

German:
- Deutschland **hat** gewonnen.
- Portugal **hat** gewonnen.
- Die Fidschi-Inseln **haben** gewonnen.
- Die Staaten **haben** gewonnen.

Italian:
- L'Italia **è** **stata** **ripescata** ai Mondiali.
- Il Portogallo **è** **stato** **ripescato** ai Mondiali.
- L'Isole Figi **sono** **state** **ripescate** ai Mondiali.
- Gli Stati Uniti **sono** **stati** **ripescati** ai Mondiali.

Spanish:
- La Italia **ha** sido **rescatada** al Mundial.
- Portugal **ha** sido **rescatado** al Mundial.
- Las Islas Fiyi **han** sido **rescatadas** al Mundial.
- Los Estados Unidos **han** sido **rescatados** al Mundial.

##### 4. Split Process Names

German:
- Der Film **beginnt** um 20 Uhr.
- Der Film **fängt** um 20 Uhr **an**.
- Der Film **endet** um 22 Uhr.
- Der Film **hört** um 22 Uhr **auf**.

##### 5. Tense

German:
- Der Film **beginnt** um 20 Uhr.
- Der Film **fängt** um 20 Uhr **an**.
- Der Film **hat** um 20 Uhr **begonnen**.
- Der Film **hat** um 20 Uhr **angefangen**.

##### 6. Case

German:
- **Ich** *liebe* **dich**.
- **Ich** *interessiere* *mich* *für* **dich**.
- **Mir** *gefällst* **du**.

##### 7. Transgrammatical semantic domains

German:
- Die Card **gilt** noch.
- Die Card **ist** noch **gültig**.

English:
- The Embassy **of the United States** in Paris
- The Embassy **of America** in Paris
- The **United States** Embassy in Paris.
- The **American** Embassy in Paris.

English:
- Ivan Rakitic scored for Barcelona in **the** **4th** minute.
- Ivan Rakitic scored for Barcelona in minute **four**.
- Ivan Rakitic scored for Barcelona after **four** minutes.

##### 8. Classification

German:
- Das Licht ist an.
- Das **Zimmer**licht ist an.
- Das **Badezimmer**licht ist an.
- Das **Bad**licht ist an.

##### 9. Participation vs classification

English:
- Ivan Rakitic scored for **Barcelona** in the 4th minute.
- **Barcelona** **player** Ivan Rakitic scored in the 4th minute.

## Other text generators
