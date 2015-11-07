# APP to Backend SMS Protocol

All fields are separated with the `|` character

Fields containing multiple values (like months) are separated with the `,`
character

Dates are in the format: `dd/MM/yyyy'

Yes/No answers are signified by one character: `y` or `n`

The maximum size of a message is 1600 characters


## Create Harvest

```

harvest_date (date) | quantity | beekeeper_clothing (y/n) |
assistant_clothing (y/n) | smoker_available (y/n) |
clean_airtight_buckets_available_number

```

## Create Inspection

```

hive (number) | inspection_date (date) | weather_condition | hive_state |
colony_strength | hive_temper | queen (y/n) | honey_store_condition |
pollen_store_condition | small_hive_beetle | varrao_mites | safari_ants (y/n) |
chalk_brood (y/n) | hive_condition | clothing_tools_condition

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


Varrao Mites
* `light`
* `moderate`
* `heavy`


Hive condition
* `good`
* `fair`
* `poor`
* `damaged`


Clothing/Tools condition
* `good`
* `fair`
* `poor`
* `damaged`
