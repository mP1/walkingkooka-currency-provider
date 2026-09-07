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

import walkingkooka.Cast;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.set.ImmutableSortedSet;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.net.UrlPath;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.text.printer.TreePrintable;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * A {@link CurrencyExchangeRaterProvider} that sources all {@link CurrencyExchangeRater} from {@link CurrencyExchangeRaters}.
 */
final class CurrencyCurrencyExchangeRaterProvider implements CurrencyExchangeRaterProvider,
    TreePrintable {

    /**
     * Factory
     */
    static CurrencyCurrencyExchangeRaterProvider with(final Function<String, Number> numberParser) {
        return new CurrencyCurrencyExchangeRaterProvider(
            Objects.requireNonNull(numberParser, "numberParser")
        );
    }

    private CurrencyCurrencyExchangeRaterProvider(final Function<String, Number> numberParser) {
        super();

        this.numberParser = numberParser;
        this.infos = CurrencyExchangeRaterInfoSet.EMPTY.setElements(
                CurrencyExchangeRaterName.NAME_TO_FACTORY.keySet()
                    .stream()
                    .map(CurrencyCurrencyExchangeRaterProvider::nameToCurrencyExchangeRaterInfo)
                    .collect(ImmutableSortedSet.collector(null))
        );
    }

    private static CurrencyExchangeRaterInfo nameToCurrencyExchangeRaterInfo(final CurrencyExchangeRaterName name) {
        return CurrencyExchangeRaterInfo.with(
            CurrencyExchangeRaterProviders.BASE_URL.appendPath(
                UrlPath.parse(
                    name.value()
                )
            ),
            name
        );
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterSelector selector,
                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(selector, "selector");

        return selector.evaluateValueText(
            this,
            context
        );
    }

    @Override
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> currencyExchangeRater(final CurrencyExchangeRaterName name,
                                                                                                   final List<?> values,
                                                                                                   final ProviderContext context) {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(values, "values");
        Objects.requireNonNull(context, "context");

        final CurrencyExchangeRaterNameFactory factory = CurrencyExchangeRaterName.NAME_TO_FACTORY.get(name);
        if (null == factory) {
            throw new IllegalArgumentException("Unknown currencyExchangeRater " + name);
        }

        return Cast.to(
            factory.create(
                Lists.immutable(values),
                this,
                context
            )
        );
    }

    // @VisibleForTesting CurrencyExchangeRaterName
    final Function<String, Number> numberParser;

    @Override
    public CurrencyExchangeRaterInfoSet currencyExchangeRaterInfos() {
        return this.infos;
    }

    private final CurrencyExchangeRaterInfoSet infos;

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        printer.println(this.getClass().getSimpleName());

        printer.indent();
        {
            TreePrintable.printTreeOrToString(
                this.currencyExchangeRaterInfos(),
                printer
            );
        }
        printer.outdent();
    }
}
