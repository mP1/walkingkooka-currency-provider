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
import walkingkooka.collect.map.Maps;
import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaters;
import walkingkooka.naming.Name;
import walkingkooka.plugin.PluginName;
import walkingkooka.plugin.PluginNameLike;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.props.Properties;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * The {@link Name} of a {@link CurrencyExchangeRater}. Note comparator names are case-sensitive.
 */
final public class CurrencyExchangeRaterName implements PluginNameLike<CurrencyExchangeRaterName> {

    public static boolean isChar(final int pos,
                                 final char c) {
        return PluginName.isChar(pos, c);
    }

    /**
     * The minimum valid length
     */
    public final static int MIN_LENGTH = 1;

    /**
     * The maximum valid length
     */
    public final static int MAX_LENGTH = PluginName.MAX_LENGTH;

    // constants........................................................................................................

    private static CurrencyExchangeRaterName registerConstantName(final String name,
                                                                  final CurrencyExchangeRaterNameFactory factory) {
        final CurrencyExchangeRaterName currencyExchangeRaterName = new CurrencyExchangeRaterName(name);
        NAME_TO_FACTORY.put(
            currencyExchangeRaterName,
            factory
        );
        return currencyExchangeRaterName;
    }

    /**
     * Holds all constants in a {@link Set}.
     */
    final static Map<CurrencyExchangeRaterName, CurrencyExchangeRaterNameFactory> NAME_TO_FACTORY = Maps.sorted();

    private final static String PROPERTIES_STRING = "properties";

    /**
     * The name of the {@link CurrencyExchangeRater} returned by {@link CurrencyExchangeRaters#properties(Properties, Function)}.
     */
    public final static CurrencyExchangeRaterName PROPERTIES = registerConstantName(
        PROPERTIES_STRING,
        new CurrencyExchangeRaterNameFactory() {
            @Override
            public CurrencyExchangeRater<?> create(final List<?> parameters,
                                                   final CurrencyCurrencyExchangeRaterProvider provider,
                                                   final ProviderContext context) {
                if (parameters.size() != 1) {
                    throw new IllegalArgumentException("Expected exactly one parameter, got " + parameters.size());
                }

                return CurrencyExchangeRaters.properties(
                    context.convertOrFail(
                        parameters.get(0),
                        Properties.class
                    ),
                    provider.numberParser
                );
            }
        }
    );

    /**
     * Factory that creates a {@link CurrencyExchangeRaterName}
     */
    public static CurrencyExchangeRaterName with(final String name) {
        Objects.requireNonNull(name, "name");

        final CurrencyExchangeRaterName currencyExchangeRaterName;

        switch (name) {
            case PROPERTIES_STRING:
                currencyExchangeRaterName = PROPERTIES;
                break;
            default:
                currencyExchangeRaterName = new CurrencyExchangeRaterName(name);
                break;
        }

        return currencyExchangeRaterName;
    }

    /**
     * Private constructor
     */
    private CurrencyExchangeRaterName(final String name) {
        super();
        this.name = PluginName.with(name);
    }

    @Override
    public String value() {
        return this.name.value();
    }

    private final PluginName name;

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof CurrencyExchangeRaterName &&
                this.equals0(Cast.to(other));
    }

    private boolean equals0(final CurrencyExchangeRaterName other) {
        return this.compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return this.name.toString();
    }

    // Json.............................................................................................................

    static CurrencyExchangeRaterName unmarshall(final JsonNode node,
                                                final JsonNodeUnmarshallContext context) {
        return with(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return JsonNode.string(this.toString());
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(CurrencyExchangeRaterName.class),
            CurrencyExchangeRaterName::unmarshall,
            CurrencyExchangeRaterName::marshall,
            CurrencyExchangeRaterName.class
        );
    }
}
