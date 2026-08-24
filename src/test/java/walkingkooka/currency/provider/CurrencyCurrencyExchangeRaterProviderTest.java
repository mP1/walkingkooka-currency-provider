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
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.Sets;
import walkingkooka.collect.set.SortedSets;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.plugin.ProviderContexts;
import walkingkooka.props.Properties;
import walkingkooka.reflect.JavaVisibility;
import walkingkooka.reflect.MethodAttributes;
import walkingkooka.text.CaseKind;

import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Function;

public final class CurrencyCurrencyExchangeRaterProviderTest implements CurrencyExchangeRaterProviderTesting<CurrencyCurrencyExchangeRaterProvider> {

    private final static Function<String, Number> NUMBER_PARSER = Double::parseDouble;

    @Override
    public CurrencyCurrencyExchangeRaterProvider createCurrencyExchangeRaterProvider() {
        return CurrencyCurrencyExchangeRaterProvider.with(
            NUMBER_PARSER
        );
    }

    @Override
    public JavaVisibility typeVisibility() {
        return JavaVisibility.PACKAGE_PRIVATE;
    }

    @Override
    public Class<CurrencyCurrencyExchangeRaterProvider> type() {
        return CurrencyCurrencyExchangeRaterProvider.class;
    }
}
