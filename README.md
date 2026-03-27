# Spring Boot Schema-based Multi-Tenant POC (MySQL)

This sample shows **schema/database-per-tenant** routing using Hibernate multi-tenancy.

## What it solves

For each request, client sends schema in header:

- `X-Schema: tenant_a`
- `X-Schema: tenant_b`

The app switches the JDBC connection to that schema before query execution. This works for:

- `JpaRepository` built-in methods (`findById`, `save`, etc.)
- JPQL queries (`@Query` without native)
- Native SQL queries (`@Query(nativeQuery = true)`)

## Run

```bash
mvn spring-boot:run
```

## Request examples

```bash
# create customer in tenant_a
curl -X POST http://localhost:8080/api/customers \
  -H 'Content-Type: application/json' \
  -H 'X-Schema: tenant_a' \
  -d '{"name":"Alice","email":"alice@acme.com"}'

# find same customer by id in tenant_a
curl http://localhost:8080/api/customers/1 -H 'X-Schema: tenant_a'

# tenant_b is isolated
curl http://localhost:8080/api/customers/1 -H 'X-Schema: tenant_b'

# jpql query
curl "http://localhost:8080/api/customers/search?name=ali" -H 'X-Schema: tenant_a'

# native query
curl "http://localhost:8080/api/customers/native?email=alice@acme.com" -H 'X-Schema: tenant_a'
```

## MySQL setup example

```sql
CREATE DATABASE tenant_default;
CREATE DATABASE tenant_a;
CREATE DATABASE tenant_b;
```

When app starts, Hibernate creates `customers` table in whichever schema is selected by request traffic.

> In production, validate schema names from trusted source (JWT claims, tenant registry) before using them.
