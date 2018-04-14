### Klic funkcije za rangiranje algoritmov

```java
String response = ResponseHandler.calculateRank(inputJson);
```

#### Vhod - JSON

```
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

```
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



