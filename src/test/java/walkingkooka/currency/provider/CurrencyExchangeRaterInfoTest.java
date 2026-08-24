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
import walkingkooka.plugin.PluginInfoLikeTesting;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

public final class CurrencyExchangeRaterInfoTest implements PluginInfoLikeTesting<CurrencyExchangeRaterInfo, CurrencyExchangeRaterName> {

    @Test
    public void testSetNameWithDifferent() {
        final AbsoluteUrl url = Url.parseAbsolute("https://example/currencyExchangeRater123");
        final CurrencyExchangeRaterName different = CurrencyExchangeRaterName.with("different");

        this.setNameAndCheck(
            CurrencyExchangeRaterInfo.with(
                url,
                CurrencyExchangeRaterName.with("original-currencyer-name")
            ),
            different,
            CurrencyExchangeRaterInfo.with(
                url,
                different
            )
        );
    }

    // ClassTesting.....................................................................................................

    @Override
    public Class<CurrencyExchangeRaterInfo> type() {
        return CurrencyExchangeRaterInfo.class;
    }

    // PluginInfoLikeTesting..............................................................................

    @Override
    public CurrencyExchangeRaterName createName(final String value) {
        return CurrencyExchangeRaterName.with(value);
    }

    @Override
    public CurrencyExchangeRaterInfo createPluginInfoLike(final AbsoluteUrl url,
                                                          final CurrencyExchangeRaterName name) {
        return CurrencyExchangeRaterInfo.with(
            url,
            name
        );
    }

    // json.............................................................................................................

    @Override
    public CurrencyExchangeRaterInfo unmarshall(final JsonNode json,
                                                final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterInfo.unmarshall(
            json,
            context
        );
    }

    // parse.............................................................................................................

    @Override
    public CurrencyExchangeRaterInfo parseString(final String text) {
        return CurrencyExchangeRaterInfo.parse(text);
    }
}
