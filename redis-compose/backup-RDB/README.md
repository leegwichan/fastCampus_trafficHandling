# Back-up by RDB

### RDB(Redis Database)를 사용한 백업
- 특정 시점의 스냅샷으로 데이터를 저장
- 재시작 시 RDB 파일이 있으면 읽어서 복구
  ![image](https://github.com/leegwichan/fastCampus_trafficHandling/assets/44027393/bc09505d-686c-44d0-8426-0c7b0025efea)

#### RDB 사용의 장점
- 상대적으로 작은 파일 사이즈로 백업 파일 관리가 용이 (원격 백업, 버전 관리 등)
- fork()를 이용해 백업하므로 서비스 중인 프로세스는 성능에 영향 없음
- 데이터 스냅샷 방식이므로 빠른 복구 가능

#### RDB 사용의 단점
- 스냅샷을 저장하는 시점 사이의 데이터 변경사항은 유실될 수 있다.
- fork()를 이용하기 떄문에 시간이 오래 걸릴 수 있고, CPU와 메모리 자원을 많이 소모
- 데이터 무결성이나 정합성에 대한 요구가 크지 않은 경우 사용 가능 (마지막 백업 시 에러 발생 등의 문제)

#### RDB 설정 방법
- 설정파일이 없어도 기본값으로 RDB를 활성화되어 있음
    - dump.rdb라는 파일이 만들어져 값을 백업하고 있음

- 저장 주기 설정 (60초마다 10개 이상의 변경이 있을 경우)
```
save 60 10
```

- 스냅샷을 저장할 파일 이름 설정
```
dbfilename dump.rdb
```

- 수동으로 스냅샷 저장
```
bgsave
```

- Docker 컨테이너 실행 시 설정 파일 적용하기
```shell
docker run -v /my/redis.conf:/redis.conf --name my-redis redis redis-server /redis.conf
```

# Back-up by RDB 실습

### config 파일 적용
- 저장 주기 설정 (60초마다 5개 이상의 변경이 있을 경우)
```
save 60 5
```

### docker 실행
```
docker-compose up --build
```

### docker 접속 및 값 변경
```
docker exec -it my-redis /bin/sh
# redis-cli
127.0.0.1:6379> set a 1
OK
```

### 결론
- 주어진 조건에 맞출때 만 변경 사항이 반영됨 (60초, 변경사항 5번)
- 즉, 실제 운영하다 보면 데이터가 일부 손실될 수 있다.