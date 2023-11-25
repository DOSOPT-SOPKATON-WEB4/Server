package org.sopt.server.service;

import lombok.RequiredArgsConstructor;
import org.sopt.server.domain.Cake;
import org.sopt.server.domain.Candle;
import org.sopt.server.domain.CandleCake;
import org.sopt.server.dto.request.CandleRequestDto;
import org.sopt.server.global.exception.CommonException;
import org.sopt.server.global.response.ErrorType;
import org.sopt.server.repository.CakeRepository;
import org.sopt.server.repository.CandleCakeRepository;
import org.sopt.server.repository.CandleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CandleService {
    private final CandleRepository candleRepository;
    private final CakeRepository cakeRepository;
    private final CandleCakeRepository cakeCandleRepository;

    @Transactional
    public void createCandle(CandleRequestDto request) {
        Candle newCandle = candleRepository.save(
                Candle.builder()
                        .title(request.title())
                        .date(request.date())
                        .feel(request.feel())
                        .body(request.body())
                        .build()
        );
        Cake cake = cakeRepository.findCakeByTitle(request.cakeTitle()).orElseThrow(()
                -> new CommonException(ErrorType.NOT_FOUND_CAKE_ERROR));
        CandleCake candleCake = cakeCandleRepository.save(
                CandleCake.builder()
                        .cake(cake)
                        .candle(newCandle)
                        .build()
                );
        cake.getCandleList().add(candleCake);
    }
}
