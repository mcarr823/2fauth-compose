# 2FAuth Compose

[![Android CI debug](https://github.com/mcarr823/2fauth-compose/actions/workflows/android.yml/badge.svg)](https://github.com/mcarr823/2fauth-compose/actions/workflows/android.yml)

2fauth-compose is an unofficial Android app for [2FAuth](https://docs.2fauth.app/).

This app connects to your self-hosted 2FAuth instance to generate 2fa tokens on your phone.

## Current state

- The API is written and mostly working/tested
- Unit tests have been written for most API requests
- Persistent storage is not yet supported
- UI is partially implemented, but needs further work

## Unit testing

In order to use or test this application, you will need an instance of 2FAuth setup somewhere.

For unit testing, or to speed up regular development/testing, you will need to update gradle.properties to include:

```
API_DOMAIN=http://my.domain.com
API_TOKEN=myapitoken
API_HTTPS_VERIFICATION=false
API_DEBUG_MODE=true
API_STORE_SECRETS=true
```

With values modified to suit your environment.
