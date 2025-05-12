#!/bin/bash

# Wait for Kafka to be ready
sleep 10

# Create the topic
kafka-topics.sh --create \
  --topic sensor-data \
  --bootstrap-server kafka:29092 \
  --replication-factor 1 \
  --partitions 1

# Keep container alive (optional)
tail -f /dev/null
