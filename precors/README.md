사전 과제 - 동시성 이슈
## **동시성 이슈 해결 방안**

동시성 처리 메커니즘
1. AtomicInteger를 이용한 원자적 연산
    - 멀티쓰레드 환경에서 안전한 정수 연산 가능
    - 메서드가 원자적으로 실행
2. CAS (Compare-And-Swap) 패턴
    - 명시적인 락없이 반복문을 사용해서 Lock-free 구현
3. ConcurrentHashMap 사용
    - 쓰레드 안전한 HashMap 구현체 사용
    - 여러 쓰레드가 동시에 읽기/쓰기 가능
4. ThreadLocal 활용
    - 각 쓰레드마다 독립적인 orderInfo 인스턴스 보관(데이터 격리)


장점
- synchronized보다 성능 좋음
- Lock-free로 동시성 제어
- 데드락 방지
- 쓰레드 안전

단점
- 경쟁이 심한 경우 CAS 실패가 반복될 수 있음(스핀 락)