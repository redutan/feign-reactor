# Feign, Reactor Sample Project

본 프로젝트는 [Scouter](https://github.com/scouter-project/scouter)에서 Feign(Hystrix 포함)과 Reactor 지원을 위해서 생성함

## Feign

Netflix 에서 만든 선언적 WebClient
    
### Hystrix

일반적으로 Good Practice의 일환으로 Feign에 Hystrix를 사용

* 이는 MSA 환경에서 장애전파차단(Circuit breaker)과 장애 극복(Fallback) 방안으로 매우 유용함 

2가지 옵션이 가능한데,

1. 세마포어로 격리 (default)
2. 스레드로 격리 (공식 문서 상에서 실제 애플리케이션에서 완벽한 격리를 위해서 이 방식을 추천함)
    * 본 샘플은 **스레드 방식으로 격리된 설정을 사용**

## Reactor

이제 대용량 애플리케이션 개발에서 반응형은 시대의 흐름임

본 샘플에서는 비동기적으로 Blocking 없이 5개의 API 요청을 동시에 처리 하므로써 처리 시간을 최소화 하는 샘플을 제공함
* 각 API는 4초 지연됨. 하지만 병렬적으로 호출하므로 4~5초 사이에 처리완료
* 개인적으로 아직까지는 전 영역에서 반응형을 할 필요는 없다고 판단하고 있으며, 특정 호출 부분에도 동기화로 인한 Blocking 지연을 최소화하는 방식으로 구성할 예정

## API

스프링부트 애플리케이션을 기동 후

### 1개 조회 
* http://localhost:8080/httpbin

### 5개 조회

* http://localhost:8080/httpbin/5x4
* 5개를 한 번에 조회 하는데 4초 간 지연이 있음. 하지만 Reactor를 이용 병렬로 조회하므로 거의 4~5초 사이에 조회 완료




