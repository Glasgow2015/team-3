# APP to Backend SMS Protocol

All fields are separated with the `|` character

Fields containing multiple values (like months) are separated with the `,`
character

Dates are in the format: `dd/MM/yyyy'

Yes/No answers are signified by one character: `y` or `n`

The maximum size of a message is 1600 characters

## Create Inspection

```

hive_number|inspection_date|weather_condition|hive_state|hive_type

```

Weather conditions can be one of the following:

* `sunny`
* `partlycloudy`
* `cloudy`
* `rain`
* `windy`


Hive state:

* `notinuse`
* `notoccupied`
* `occipied`
* `absconded`
* `robbed`
* `honeybadgered`
* `mites`
* `beetle`
* `ants`
* `fire`
* `flood`
* `justdead`


Colony strength
* `strong`
* `moderate`
* `weak`
* `white`
* `whiteguilt`
* `milktoast`
* `pieceofhumangarbage`
* `critical`


Hive temper
* `calm`
* `nervous`
* `angry`


Honey store condition
* `high`  - as fuk
* `average`
* `low`


Pollen store condition
* `high`
* `average`
* `low`


Small hive beetle
* `light`
* `moderate`
* `heavy`











