**Vytvořte aplikaci využívající synchronizační primitiva ( semafor, zámek/monitor) k vytvoření aplikace, která bude simulovat provoz v restauraci**

Máme stůl, kde je 5 hostů
Máme 3 chody - polévku, hlavní jídlo a zákusek, tyto budou servírovány v tomto pořadí
V restauraci je 7 číšníků. Současně obsluhovat ale může maximálně tolik číšníků, kolik je hostů
Proces servírování je v následujících krocích:
- Číšník beze jídlo v kuchyni - prodleva 100ms
- Číšník servíruje - prodleva 100ms
- Číšník si jde oddechnout - prodleva 500ms
- Každý host musí dostat postupně vše
- Host konzumuje každé jídlo 1000ms

Jednoho hosta obsluhuje v dané chvíli jen jeden číšník
Aplikace skončí v okamžiku, kdy všichni hosté dostali postupně vše
Aplikace bude vypisovat jednotlivé stavy číšníků a hostů:
- číšník X bere JIDLO
- číšník X servíruje JIDLO pro hosta Y
- host Y konzumuje JIDLO
