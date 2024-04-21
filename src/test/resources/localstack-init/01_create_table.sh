#!/bin/bash
awslocal dynamodb create-table \
    --table-name StarshipConfiguration \
    --attribute-definitions AttributeName=id,AttributeType=S \
    --key-schema AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
