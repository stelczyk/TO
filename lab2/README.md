# Lab 2

W `main()` są trzy wektory:
- **A** = [3, 4] – adapter (`Polar2DAdapter`)
- **B** = [1, 2, 3] – dekorator (`Vector3DDecorator`)
- **C** = [2, -1, 4] – dziedziczenie (`Vector3DInheritance`)

Program wypisuje współrzędne kartezjańskie i biegunowe każdego wektora oraz iloczyn skalarny i wektorowy dla wszystkich par. Iloczyn wektorowy liczony jest metodą macierzową (wyznacznik). Wektor 2D ma z = 0.

## Wady i zalety

**Dziedziczenie** - nowa klasa rozszerza starą (`extends`).
- Zaleta: Proste, mało kodu, metody rodzica są „za darmo”.
- Wada: Sztywne - ustalone przy kompilacji, tylko jeden rodzic, nie da się rozszerzyć już istniejącego obiektu.

**Dekorator** - obiekt owija inny obiekt i dokłada coś od siebie.
- Zaleta: Elastyczny - działa w trakcie programu i z dowolnym `IVector`, oryginał zostaje bez zmian.
- Wada: Więcej obiektów i kodu, trzeba przekazywać wywołania do środka.

**Adapter** - obiekt owija inny obiekt, żeby pasował do innego interfejsu.
- Zaleta: Pozwala użyć klasy tam, gdzie wymagany jest inny interfejs, bez zmieniania jej kodu.
- Wada: Dodatkowa warstwa - kolejna klasa tylko do „tłumaczenia”.

## Wniosek
Dziedziczenie jest najprostsze, ale najmniej elastyczne. Dekorator i adapter są elastyczniejsze, bo działają na obiektach, a nie na klasach, kosztem trochę większej ilości kodu.
