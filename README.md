### Klic funkcije za rangiranje algoritmov

```java
String response = ResponseHandler.calculateRank(inputJson);
```

#### Vhod - JSON

```json
{
	"alpha": 0.5,
	"method": "KS",
	"data":[
		{
			"name":"algorithmName1",
			"problems":[
				{
					"name":"problemName1",
					"data":[11.0,2.0,13.0,4.0,15.0]
				},
				{
					"name":"problemName2",
					"data":[1.0,7.0,8.0,9.0,4.0]
				}
			]
		},
		{
			"name":"algorithmName2",
			"problems":[
				{
					"name":"problemName1",
					"data":[1.0,2.0,3.0,4.0,5.0]
				},
				{
					"name":"problemName2",
					"data":[6.0,7.0,7.0,9.0,11.0]
				}
			]
		}
	]
}
```

### Veljavne vrednosti za parameter "method"

* AD/AndersonDarling
* KS/KolmogorovSmirnov


#### Izhod - JSON

```json
{
	"ranked_matrix":[
		{
			"problemName":"problemName1",
			"result":[
				{
					"algorithmName":"algorithmName1",
					"rank":1.5
				},
				{
					"algorithmName":"algorithmName2",
					"rank":1.5
				}
			]
		},
		{
			"problemName":"problemName2",
			"result":[
				{
					"algorithmName":"algorithmName1",
					"rank":1.5
				},
				{
					"algorithmName":"algorithmName2",
					"rank":1.5
				}
			]
		}
	],
	"number_algorithms":2,
	"parametric_tests":0,
	"method":{
		"name":"methodName",
		"alpha":0.05
	}
}
```


### Uporaba za potrjevanje hipoteze

```java
String response = ResponseHandler.calculatePValue(inputJson);
```

#### Vhod - JSON

```json
{
	"ranked_matrix": [{
		"problemName": "f1",
		"result": [{
			"algorithmName": "BSqi",
			"rank": 2.0
		}, {
			"algorithmName": "BSrr",
			"rank": 2.0
		}, {
			"algorithmName": "Srr",
			"rank": 6.0
		}, {
			"algorithmName": "Sifeg",
			"rank": 6.0
		}, {
			"algorithmName": "BSif",
			"rank": 2.0
		}, {
			"algorithmName": "CMA.CSA",
			"rank": 6.0
		}, {
			"algorithmName": "RAND.2xDefault",
			"rank": 9.0
		}, {
			"algorithmName": "CMA.TPA",
			"rank": 6.0
		}, {
			"algorithmName": "GP1.CMAES",
			"rank": 6.0
		}, {
			"algorithmName": "RF5.CMAES",
			"rank": 10.0
		}]
	}, {
		"problemName": "f22",
		"result": [{
			"algorithmName": "BSqi",
			"rank": 5.5
		}, {
			"algorithmName": "BSrr",
			"rank": 5.5
		}, {
			"algorithmName": "Srr",
			"rank": 5.5
		}, {
			"algorithmName": "Sifeg",
			"rank": 5.5
		}, {
			"algorithmName": "CMA.CSA",
			"rank": 5.5
		}, {
			"algorithmName": "BSif",
			"rank": 5.5
		}, {
			"algorithmName": "RAND.2xDefault",
			"rank": 5.5
		}, {
			"algorithmName": "CMA.TPA",
			"rank": 5.5
		}, {
			"algorithmName": "GP1.CMAES",
			"rank": 5.5
		}, {
			"algorithmName": "RF5.CMAES",
			"rank": 5.5
		}]
	}],
	"number_algorithms": 10,
	"parametric_tests": 0,
	"method": {
		"name": "F",
		"alpha": 0.05
	}
}
```

#### Izhod - JSON


```json
{
	"message": "The null hypothesis is rejected.",
	"p_value": 2.6567636979279996E-13,
	"t": 78.91239669421483,
	"method": {
		"name": "F",
		"alpha": 0.05
	},
	"algorithm_means": [{
		"name": "BSif",
		"mean": 6.295454545454546
	}, {
		"name": "BSqi",
		"mean": 5.318181818181818
	}, {
		"name": "BSrr",
		"mean": 5.818181818181818
	}, {
		"name": "CMA.CSA",
		"mean": 2.522727272727273
	}, {
		"name": "CMA.TPA",
		"mean": 2.9545454545454546
	}, {
		"name": "GP1.CMAES",
		"mean": 5.2727272727272725
	}, {
		"name": "RAND.2xDefault",
		"mean": 8.022727272727273
	}, {
		"name": "RF5.CMAES",
		"mean": 8.659090909090908
	}, {
		"name": "Sifeg",
		"mean": 5.045454545454546
	}, {
		"name": "Srr",
		"mean": 5.090909090909091
	}]
}
```


### Posthoc analiza

```java
String response = ResponseHandler.postHoc(inputJson);
```

#### Vhod - JSON

```json
{
	"algorithm_means": [{
		"name": "BSif",
		"mean": 6.295454545454546
	}, {
		"name": "BSqi",
		"mean": 5.318181818181818
	}, {
		"name": "BSrr",
		"mean": 5.818181818181818
	}, {
		"name": "CMA.CSA",
		"mean": 2.522727272727273
	}, {
		"name": "CMA.TPA",
		"mean": 2.9545454545454546
	}, {
		"name": "GP1.CMAES",
		"mean": 5.2727272727272725
	}, {
		"name": "RAND.2xDefault",
		"mean": 8.022727272727273
	}, {
		"name": "RF5.CMAES",
		"mean": 8.659090909090908
	}, {
		"name": "Sifeg",
		"mean": 5.045454545454546
	}, {
		"name": "Srr",
		"mean": 5.090909090909091
	}],
	"k": 10,
	"n": 22,
	"base_algorithm": "CMA.CSA",
	"method": "F"
}
```

#### Izhod - JSON


```json
{
	"adjustments": [{
		"name": "ZValue",
		"algorithms": [{
			"algorithm": "Sifeg",
			"value": -2.763509267412429
		}, {
			"algorithm": "BSif",
			"value": -4.132815661175345
		}, {
			"algorithm": "GP1.CMAES",
			"value": -3.012474066278413
		}, {
			"algorithm": "Srr",
			"value": -2.8133022271856256
		}, {
			"algorithm": "RF5.CMAES",
			"value": -6.722049569381582
		}, {
			"algorithm": "BSqi",
			"value": -3.0622670260516105
		}, {
			"algorithm": "RAND.2xDefault",
			"value": -6.024948132556827
		}, {
			"algorithm": "BSrr",
			"value": -3.6099895835567763
		}, {
			"algorithm": "CMA.TPA",
			"value": -0.47303311784537055
		}]
	}, {
		"name": "UnadjustedPValue",
		"algorithms": [{
			"algorithm": "Sifeg",
			"value": 0.00285917342346042
		}, {
			"algorithm": "BSif",
			"value": 1.7917306114404924E-5
		}, {
			"algorithm": "GP1.CMAES",
			"value": 0.0012956378802662768
		}, {
			"algorithm": "Srr",
			"value": 0.0024517769811942852
		}, {
			"algorithm": "RF5.CMAES",
			"value": 8.959298436489356E-12
		}, {
			"algorithm": "BSqi",
			"value": 0.0010983369889655701
		}, {
			"algorithm": "RAND.2xDefault",
			"value": 8.458193208339883E-10
		}, {
			"algorithm": "BSrr",
			"value": 1.5310465084647748E-4
		}, {
			"algorithm": "CMA.TPA",
			"value": 0.31809477587118723
		}]
	}, {
		"name": "Holm",
		"algorithms": [{
			"algorithm": "Sifeg",
			"value": 0.007355330943582856
		}, {
			"algorithm": "BSif",
			"value": 1.2542114280083446E-4
		}, {
			"algorithm": "GP1.CMAES",
			"value": 0.005491684944827851
		}, {
			"algorithm": "Srr",
			"value": 0.007355330943582856
		}, {
			"algorithm": "RF5.CMAES",
			"value": 8.063368592840421E-11
		}, {
			"algorithm": "BSqi",
			"value": 0.005491684944827851
		}, {
			"algorithm": "RAND.2xDefault",
			"value": 6.766554566671906E-9
		}, {
			"algorithm": "BSrr",
			"value": 9.186279050788649E-4
		}, {
			"algorithm": "CMA.TPA",
			"value": 0.31809477587118723
		}]
	}, {
		"name": "Hochberg",
		"algorithms": [{
			"algorithm": "Sifeg",
			"value": 0.00571834684692084
		}, {
			"algorithm": "BSif",
			"value": 1.2542114280083446E-4
		}, {
			"algorithm": "GP1.CMAES",
			"value": 0.005182551521065107
		}, {
			"algorithm": "Srr",
			"value": 0.00571834684692084
		}, {
			"algorithm": "RF5.CMAES",
			"value": 8.063368592840421E-11
		}, {
			"algorithm": "BSqi",
			"value": 0.005182551521065107
		}, {
			"algorithm": "RAND.2xDefault",
			"value": 6.766554566671906E-9
		}, {
			"algorithm": "BSrr",
			"value": 9.186279050788649E-4
		}, {
			"algorithm": "CMA.TPA",
			"value": 0.31809477587118723
		}]
	}]
}
```


