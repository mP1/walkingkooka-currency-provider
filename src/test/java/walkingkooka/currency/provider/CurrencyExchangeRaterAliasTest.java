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
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.net.Url;
import walkingkooka.plugin.PluginAliasLikeTesting;
import walkingkooka.reflect.JavaVisibility;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class CurrencyExchangeRaterAliasTest implements PluginAliasLikeTesting<CurrencyExchangeRaterName, CurrencyExchangeRaterSelector, CurrencyExchangeRaterAlias> {

    private final static CurrencyExchangeRaterName NAME = CurrencyExchangeRaterName.with("hello");

    private final static Optional<CurrencyExchangeRaterSelector> SELECTOR = Optional.of(
        CurrencyExchangeRaterSelector.parse("currency-exchange-rater-123")
    );

    private final static Optional<AbsoluteUrl> URL = Optional.of(
        Url.parseAbsolute("https://example.com/currency-exchange-rater-123")
    );

    // with.............................................................................................................

    @Test
    public void testWithNullNameFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterAlias.with(
                null,
                SELECTOR,
                URL
            )
        );
    }

    @Test
    public void testWithNullSelectorFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterAlias.with(
                NAME,
                null,
                URL
            )
        );
    }

    @Test
    public void testWithNullUrlFails() {
        assertThrows(
            NullPointerException.class,
            () -> CurrencyExchangeRaterAlias.with(
                NAME,
                SELECTOR,
                null
            )
        );
    }

    // parse............................................................................................................

    @Test
    public void testParse() {
        this.parseStringAndCheck(
            "alias1 name1 https://example.com",
            CurrencyExchangeRaterAlias.with(
                CurrencyExchangeRaterName.with("alias1"),
                Optional.of(
                    CurrencyExchangeRaterSelector.parse("name1")
                ),
                Optional.of(
                    Url.parseAbsolute("https://example.com")
                )
            )
        );
    }

    @Override
    public CurrencyExchangeRaterAlias parseString(final String text) {
        return CurrencyExchangeRaterAlias.parse(text);
    }

    // Comparable.......................................................................................................

    @Override
    public CurrencyExchangeRaterAlias createComparable() {
        return CurrencyExchangeRaterAlias.with(
            NAME,
            SELECTOR,
            URL
        );
    }

    // class............................................................................................................

    @Override
    public Class<CurrencyExchangeRaterAlias> type() {
        return CurrencyExchangeRaterAlias.class;
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PUBLIC;
    }
}
