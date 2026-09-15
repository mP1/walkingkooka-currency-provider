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

import walkingkooka.text.printer.TreePrintableTesting;

import java.util.Optional;

public interface HasOptionalCurrencyExchangeRaterSelectorTesting extends TreePrintableTesting {

    default void currencyExchangeRaterSelectorAndCheck(final HasOptionalCurrencyExchangeRaterSelector has) {
        this.currencyExchangeRaterSelectorAndCheck(
            has,
            Optional.empty()
        );
    }

    default void currencyExchangeRaterSelectorAndCheck(final HasOptionalCurrencyExchangeRaterSelector has,
                                                       final CurrencyExchangeRaterSelector expected) {
        this.currencyExchangeRaterSelectorAndCheck(
            has,
            Optional.of(expected)
        );
    }

    default void currencyExchangeRaterSelectorAndCheck(final HasOptionalCurrencyExchangeRaterSelector has,
                                                       final Optional<CurrencyExchangeRaterSelector> expected) {
        this.checkEquals(
            expected,
            has.currencyExchangeRaterSelector(),
            () -> has + " currencyExchangeRaterSelector()"
        );
    }
}
