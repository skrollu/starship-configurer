.aws/config

[profile local]
endpoint_url = http://localhost:8000
region = eu-central-1
output = json

.aws/credentials

[local]
aws_access_key_id = anykey
aws_secret_access_key = anysecret

docker volume create dynamodb-volume

https://dev.to/aws-heroes/dynamodb-local-in-docker-25i
https://docs.aws.amazon.com/fr_fr/amazondynamodb/latest/developerguide/DynamoDBLocal.UsageNotes.html
https://stackoverflow.com/questions/29558948/dynamo-local-from-node-aws-all-operations-fail-cannot-do-operations-on-a-non-e
-sharedDb — DynamoDB Local will use a single database file, instead of using separate files for each credential and region. If you specify -sharedDb, all DynamoDB Local clients will interact with the same set of tables regardless of their region and credential configuration.
docker run -d --name dynamodb -v dynamodb-volume:/home/dynamodblocal -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath .

docker run --rm -d --name dynamodb -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -sharedDb -dbPath /home/dynamodblocal

aws dynamodb create-table --table-name Fruits --attribute-definitions AttributeName=fruitName,AttributeType=S --key-schema AttributeName=fruitName,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --profile local

aws dynamodb scan --table-name Fruits --profile local

aws dynamodb list-tables --profile local

# StarshipConfigurer

Tools to customize your own starship with different component. Add components like hyperdrive system, engines, shields, weapons, tank, and more. Compose your starship based on a chassis through our great list of items.

## Action available

- list components per type.
- dynamically update available items considering precedent choices.
- save new starship as custom ship
- mark some component as mandatory (chassis + tank + engines + reactor)
