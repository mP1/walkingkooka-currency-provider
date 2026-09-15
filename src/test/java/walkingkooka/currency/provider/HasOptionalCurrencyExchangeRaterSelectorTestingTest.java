/*
 * Copyright 2026 Miroslav Pokorny (github.com/mP1)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package walkingkooka.currency.provider;

import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.util.Optional;

public final class HasOptionalCurrencyExchangeRaterSelectorTestingTest implements HasOptionalCurrencyExchangeRaterSelectorTesting {

    @Test
    public void testCurrencyExchangeRaterSelectorAndCheckWithNone() {
        this.currencyExchangeRaterSelectorAndCheck(
            () -> HasOptionalCurrencyExchangeRaterSelector.NO_CURRENCY_EXCHANGE_RATER_SELECTOR
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorAndCheck() {
        final CurrencyExchangeRaterSelector selector = CurrencyExchangeRaterSelector.parse("hello");

        this.currencyExchangeRaterSelectorAndCheck(
            () -> Optional.of(selector),
            selector
        );
    }

    @Test
    public void testCurrencyExchangeRaterSelectorAndCheckFails() {
        boolean failed = false;
        try {
            this.currencyExchangeRaterSelectorAndCheck(
                () ->
                    Optional.of(
                        CurrencyExchangeRaterSelector.parse("hello")
                    ),
                CurrencyExchangeRaterSelector.parse("different")
            );
        } catch (final AssertionFailedError expected) {
            failed = true;
        }
        this.checkEquals(
            true,
            failed
        );
    }
}
