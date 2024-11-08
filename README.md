# 구현할 기능 목록

## 1. 도메인 구현
### Product 관련 기능
- [x] 상품 기본 정보 관리
  - 이름, 가격, 수량, 프로모션명 포함
  - 유효하지 않은 값 검증
    - 이름이 null이거나 빈 값인 경우
    - 가격이 0 이하인 경우
    - 수량이 0 미만인 경우
  -[x] 재고 감소 기능 추가
    - 재고 부족시 예외 처리
  -[x] 프로모션 재고 관리
    - 프로모션 재고 필드 추가
    - 프로모션 재고 설정/감소 기능 구현
    - 프로모션 재고 부족 시 예외 처리
### Promotion 관련 기능
- [x] 프로모션 기본 정보 관리
  - 이름, 시작일, 종료일, 필요 구매 수량 포함
  - 유효하지 않은 값 검증
    - 이름이 null이거나 빈 값인 경우
    - 시작일/종료일이 null인 경우
    - 시작일이 종료일보다 늦은 경우
    - 구매 필요 수량이 1 미만인 경우
- [ ] 프로모션 기간 검증
  - DateTimes.now()를 사용한 현재 날짜 검증
### Order 관련 기능
- [x] 주문 기본 정보 관리
- 상품명, 수량 포함
- 유효하지 않은 값 검증
  - 상품명이 null이거나 빈 값인 경우
  - 수량이 1 미만인 경우
## 2. 파일 입출력
- [ ] products.md 파일 읽기
- [ ] promotions.md 파일 읽기

## 3. 사용자 입력 처리
- [ ] 상품 주문 입력
- [ ] 프로모션 관련 입력
- [ ] 멤버십 할인 관련 입력
- [ ] 추가 구매 관련 입력

## 4. service 관련 구현
### OrderCalculator 관련 기능
- [x] 주문 가격 계산
- 상품 가격 * 수량
### PromotionDiscount 관련 기능
- [ ] 2+1 할인 계산
  - 3개 단위로 1개 무료 계산
- [ ] 멤버십 할인
- [ ] 최종 금액 계산

## 5. 출력 기능
- [ ] 상품 목록 출력
- [ ] 영수증 출력