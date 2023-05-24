### Unit 04. Redis Sentinel을 이용한 자동 장애조치

#### Redis Sentinel
- Redis에서 HA(high availability)를 제공하기 위한 장치
- master-replica 구조에서 master가 다운 시 replica를 master로 승격시키는 auto-failover를 수행함
- Sentinel의 기능 : 모니터링, 알림, 자동 장애 복구, 환경 설정 제공자

#### Redis Sentinal 구성도
- Sentinal 노드는 3개 이상으로 구성됨 ([Quorum](https://crystalcube.co.kr/177) 때문)
- Sentinal 들은 서로 연결되어 있음
- Sentinal 들을 Redis master와 replica를 모니터링
- Client는 Sentinal을 통해 Redis에 접근함
  ![image](https://github.com/leegwichan/fastCampus_trafficHandling/assets/44027393/bb405d1b-42e8-4a4e-ac6f-243d1affd0de)

#### Redis Sentinal의 특징
- SDOWN과 ODOWN의 2가지 판단이 있음
    - SDOWN : Sentinal 1대가 down으로 판단 (주관적)
    - ODOWN : 정족수가 충족되어 down으로 판단 (객관적)
- master 노드가 down된걸로 판단되기 위해서는 Sentinal 노드들이 정족수(Quorum)을 충족해야 함
- Client는 Sentinal을 통해 master의 주소를 얻어내야 함

#### 복구 상황
- 우선 master와 replica가 있는 상황에서 master가 다운된다면
    - Sentinal에서 Quorum 이상의 개수가 반응을 한다면, replica가 master가 되는 승격 과정을 진행
    - 승격 이후에도 이전 master의 상태를 지속적으로 감지
    - 이전 master가 다시 정상 작동 한다면, 현재 실행 중인 master의 replica가 된다.

#### [Spring에서 사용 방법](./application.yml)
