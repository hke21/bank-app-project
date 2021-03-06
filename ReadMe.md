1. Start docker desktop
2. run ‘minikube start’
3. run ‘kubectl apply -f deployments.yaml’
4. run ‘kubectl get all’ to check that pods are running 
5. run ‘kubectl apply -f ingressGateway.yaml’

Load balancing
1. Run ‘kubectl apply -f deploymentsWith3Pods.yaml’ to have 3 pods of user-validator-service
2. Run ‘kubectl apply -f loadBalancing.yaml’
3. Check Jaeger traces to confirm load balancer is up and running

Timeout (and fault injection with delay)
1. Run ‘kubectl apply -f delayForTimeout.yaml’ to introduce a delay between services
2. Run ‘kubectl apply -f timeout.yaml’
3. Check in Kiali if both configurations are applied
![alt text](https://github.com/hke21/bank-app-project/blob/main/assets/1.png?raw=true)
4. Make a request in Postman and check response status code
![alt text](https://github.com/hke21/bank-app-project/blob/main/assets/2.png?raw=true)


Retry
1. Run ‘kubectl apply -f retry.yaml’
2. Run “curl -X POST http://localhost/users-possible-bad-gateway -H 'Content-Type: application/json' -d '{"firstName": "FirstName1", "lastName": "LastName1", "phoneNumber": "06601111111", "citizenship": "Citizenship1”}’”
3. Go to Jaeger and confirm 
![alt text](https://github.com/hke21/bank-app-project/blob/main/assets/3.png?raw=true)

Circuit breaker
	Connection pool
1. Run ‘kubectl apply -f circuitBreakerUserValidatorService.yaml’
2. Run ‘curl -I "http://localhost/get-test" & curl -I "http://localhost/get-test”’
3. One of the async request has failed
![alt text](https://github.com/hke21/bank-app-project/blob/main/assets/4.png?raw=true)

	Outlier detection
1. Run ‘kubectl apply -f deployments.yaml’ to configure only one pod of user-validator-service running
2. Run 4 requests ‘curl -X POST http://localhost/users-bad-gateway  -H 'Content-Type: application/json' -d '{"firstName": "FirstName1", "lastName": "LastName1", "phoneNumber": "06601111111", "citizenship": "Citizenship1"}'’
3. 4th one should return 503 SERVICE_UNAVAILABLE, which can be checked in logs of bank-client-app
