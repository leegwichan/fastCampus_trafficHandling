# Back-up by AOF

- AOP(Append Only File)
    - 모든 쓰기 요청에 대한 로그를 저장
    - 재시작 시 AOF에 기록된 모든 동작을 재수행해서 데이터를 복구
      ![image](https://github.com/leegwichan/fastCampus_trafficHandling/assets/44027393/0357b3da-3ce9-44de-96a2-e6e07d500641)

- AOF의 장점
    - 모든 변경사항이 기록되므로 RDB 방식 대비 안정적으로 데이터 백업 가능
    - AOF 파일은 append-only 방식이므로 백업 파일이 손상될 위험이 적음
    - 실제 수행된 명령어가 저장되어 있으므로 사람이 보고 이해할 수 있고 수정도 가능

- AOF의 단점
    - RDB 방식보다 파일 사이즈가 커짐
    - RDB 방식 대비 백업&복구 속도가 느림

#### AOF 관련 개념

- Log rewriting: 최종 상태를 만들기 위한 최소한의 로그만 남기기 위해 일부를 새로 씀
    - Ex: 1개의 key값을 100번 수정해도 최종 상태는 1개이므로 SET 1개로 대체 가능
- Multi Part AOF: Redis 7.0부터 AOF가 단일 파일에 저장되지 않고 여러 개가 사용됨
    - base file: 마지막 rewrite 시의 스냅샷을 저장
    - incremental file: 마지막으로 base file이 생성된 이후의 변경사항이 쌓임
    - manifest file: 파일들을 관리하기 위한 메타 데이터를 저장

#### config 파일 설정 방법

- AOF 사용 (default : no)
```
appendonly yes
```
- AOF 파일 이름
```
appendfilename appendonly.aof
```
- fsync 정책 설정 (always, everysec, no)
    - always : 새로운 커맨드가 추가될 때마다 수행. 가장 안전하지만 가장 느림.
    - everysec : 1초마다 수행. 성능은 RDB 수준에 근접.
    - no : OS에 맡김. 가장 빠르지만 덜 안전한 방법.(커널마다 수행 시간이 다를 수 있음)
```
appendfsync everysec
```

# Back-up by AOF 실습

### config 파일 적용
- RDB(Redis Database) Disable
- append only 설정을 yes로 함 (관련 파일 이름, 폴더 지정 가능)
```text
save ""
appendonly yes

appendfilename "appendonly.aof"
appenddirname "appendonlydir"
```

### data 추가하기
```shell
docker exec -it my-redis-aof /bin/sh
redis-cli
set a 1
exit
```

### 결과

- Docker 안의 /data 폴더에 appendonlydir 파일이 생성됨
- Docker를 다시 실행해도 data는 남아 있음
