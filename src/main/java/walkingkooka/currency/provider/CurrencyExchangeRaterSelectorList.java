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

import walkingkooka.collect.list.CsvStringList;
import walkingkooka.collect.list.HasCsvStringListTsvStringList;
import walkingkooka.collect.list.ImmutableListDefaults;
import walkingkooka.collect.list.Lists;
import walkingkooka.collect.list.StringLikeList;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A {@link List} that holds non-null {@link CurrencyExchangeRaterSelector currencyExchangeRaterSelector}.
 */
public final class CurrencyExchangeRaterSelectorList extends AbstractList<CurrencyExchangeRaterSelector>
    implements ImmutableListDefaults<CurrencyExchangeRaterSelectorList, CurrencyExchangeRaterSelector>,
    HasCsvStringListTsvStringList {

    public static CurrencyExchangeRaterSelectorList fromStringLikeList(final StringLikeList stringLikeList) {
        Objects.requireNonNull(stringLikeList, "stringLikeList");

        return EMPTY.setElements(
            stringLikeList.stream()
                .map(CurrencyExchangeRaterSelector::parse)
                .collect(Collectors.toList())
        );
    }

    public final static CurrencyExchangeRaterSelectorList EMPTY = new CurrencyExchangeRaterSelectorList(Lists.empty());

    // @VisibleForTesting
    CurrencyExchangeRaterSelectorList(final List<CurrencyExchangeRaterSelector> list) {
        super();
        this.list = list;
    }

    @Override
    public CurrencyExchangeRaterSelector get(final int index) {
        return this.list.get(index);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public CurrencyExchangeRaterSelectorList setElements(final Collection<CurrencyExchangeRaterSelector> collection) {
        final CurrencyExchangeRaterSelectorList currencyList;

        if (collection instanceof CurrencyExchangeRaterSelectorList) {
            currencyList = (CurrencyExchangeRaterSelectorList) collection;
        } else {
            Objects.requireNonNull(collection, "collection");

            final List<CurrencyExchangeRaterSelector> copy = Lists.array();
            for (final CurrencyExchangeRaterSelector currencyExchangeRaterSelector : collection) {
                this.elementCheck(currencyExchangeRaterSelector);

                copy.add(currencyExchangeRaterSelector);
            }

            if (copy.equals(this)) {
                currencyList = this;
            } else {
                currencyList = copy.isEmpty() ?
                    EMPTY :
                    new CurrencyExchangeRaterSelectorList(copy);
            }
        }

        return currencyList;
    }

    @Override
    public void elementCheck(final CurrencyExchangeRaterSelector currencyExchangeRaterSelector) {
        Objects.requireNonNull(currencyExchangeRaterSelector, "currencyExchangeRaterSelector");
    }

    private final List<CurrencyExchangeRaterSelector> list;

    // HasCsvString.....................................................................................................

    /**
     * Returns a {@link CsvStringList}, with each value a single {@link CurrencyExchangeRaterSelector} after converting to CSV.
     */
    @Override
    public CsvStringList csvStringList() {
        return CsvStringList.makeCsvStringList(
            this.list,
            CurrencyExchangeRaterSelector::text
        );
    }

    // json.............................................................................................................

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(CurrencyExchangeRaterSelectorList.class),
            CurrencyExchangeRaterSelectorList::unmarshall,
            CurrencyExchangeRaterSelectorList::marshall,
            CurrencyExchangeRaterSelectorList.class
        );
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return context.marshall(this.csvStringList());
    }

    static CurrencyExchangeRaterSelectorList unmarshall(final JsonNode node,
                                                        final JsonNodeUnmarshallContext context) {
        return CurrencyExchangeRaterSelectorList.fromStringLikeList(
            context.unmarshall(
                node,
                CsvStringList.class
            )
        );
    }
}
