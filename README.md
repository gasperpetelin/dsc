### Klic funkcije za rangiranje algoritmov

```java
String response = ResponseHandler.calculateRank(inputJson);
```

#### Vhod - JSON

```json
{
	"alpha": 0.5,
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

