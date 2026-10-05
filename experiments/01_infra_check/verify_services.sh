#!/bin/bash
echo "=== Running Week 1 Infrastructure Latency & Health Checks ==="

# Postgres Ping
echo -n "PostgreSQL (5432): "
nc -zv -w 2 localhost 5432 2>&1 | grep -q "succeeded" && echo "ONLINE" || echo "OFFLINE"

# Redis Ping
echo -n "Redis Ping (6379): "
docker exec companion-redis redis-cli ping 2>/dev/null || echo "OFFLINE"

# OpenSearch Cluster Health
echo -n "OpenSearch Status (9200): "
curl -s http://localhost:9200/_cluster/health | grep -q '"status"' && echo "HEALTHY" || echo "UNHEALTHY"
