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

import walkingkooka.currency.CurrencyExchangeRater;
import walkingkooka.currency.CurrencyExchangeRaterContext;
import walkingkooka.net.header.HasContentType;
import walkingkooka.net.header.MediaType;
import walkingkooka.plugin.PluginSelector;
import walkingkooka.plugin.PluginSelectorLike;
import walkingkooka.plugin.ProviderContext;
import walkingkooka.text.printer.IndentingPrinter;
import walkingkooka.tree.json.JsonNode;
import walkingkooka.tree.json.marshall.JsonNodeContext;
import walkingkooka.tree.json.marshall.JsonNodeMarshallContext;
import walkingkooka.tree.json.marshall.JsonNodeUnmarshallContext;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Contains the {@link CurrencyExchangeRaterName} and some text which may contain an expression for a {@link CurrencyExchangeRater}.
 */
public final class CurrencyExchangeRaterSelector implements PluginSelectorLike<CurrencyExchangeRaterName> {

    /**
     * Parses the given text into a {@link CurrencyExchangeRaterSelector}. Note the text following the {@link CurrencyExchangeRaterName} is not validated in any form and simply stored.
     */
    public static CurrencyExchangeRaterSelector parse(final String text) {
        return new CurrencyExchangeRaterSelector(
            PluginSelector.parse(
                text,
                CurrencyExchangeRaterName::with
            )
        );
    }

    /**
     * Factory that creates a new {@link CurrencyExchangeRaterSelector}.
     */
    public static CurrencyExchangeRaterSelector with(final CurrencyExchangeRaterName name,
                                                     final String text) {
        return new CurrencyExchangeRaterSelector(
            PluginSelector.with(
                name,
                text
            )
        );
    }

    private CurrencyExchangeRaterSelector(final PluginSelector<CurrencyExchangeRaterName> selector) {
        this.selector = selector;
    }

    // HasName..........................................................................................................

    @Override
    public CurrencyExchangeRaterName name() {
        return this.selector.name();
    }

    /**
     * Would be setter that returns a {@link CurrencyExchangeRaterSelector} with the given {@link CurrencyExchangeRaterName},
     * creating a new instance if necessary.
     */
    @Override
    public CurrencyExchangeRaterSelector setName(final CurrencyExchangeRaterName name) {
        Objects.requireNonNull(name, "name");

        return this.name().equals(name) ?
            this :
            new CurrencyExchangeRaterSelector(
                PluginSelector.with(
                    name,
                    this.valueText()
                )
            );
    }

    // HasText..........................................................................................................

    /**
     * If the {@link CurrencyExchangeRaterName} identifies a {@link CurrencyExchangeRater}, this will
     * hold the pattern text itself.
     */
    @Override
    public String valueText() {
        return this.selector.valueText();
    }

    @Override
    public CurrencyExchangeRaterSelector setValueText(final String text) {
        final PluginSelector<CurrencyExchangeRaterName> different = this.selector.setValueText(text);
        return this.selector.equals(different) ?
            this :
            new CurrencyExchangeRaterSelector(different);
    }

    private final PluginSelector<CurrencyExchangeRaterName> selector;

    // setValues........................................................................................................

    @Override
    public CurrencyExchangeRaterSelector setValues(final List<?> values) {
        final PluginSelector<CurrencyExchangeRaterName> different = this.selector.setValues(values);
        return this.selector.equals(different) ?
            this :
            new CurrencyExchangeRaterSelector(different);
    }

    // evaluateText.....................................................................................................

    /**
     * Parses the {@link #valueText()}  as an expression that contains an optional parameter list which may include
     * <ul>
     * <li>{@link CurrencyExchangeRaterName}</li>
     * <li>double literals including negative or leading minus signs.</li>
     * <li>a double quoted string literal</li>
     * </ul>
     * Sample text.
     * <pre>
     * number-to-number
     * collection ( number-to-boolen, number-number, string-to-local-date "yyyy-mm-dd")
     * </pre>
     * The {@link CurrencyExchangeRaterProvider} will be used to fetch {@link CurrencyExchangeRater} with any parameters.
     */
    public <C extends CurrencyExchangeRaterContext> CurrencyExchangeRater<C> evaluateValueText(final CurrencyExchangeRaterProvider provider,
                                                                                               final ProviderContext context) {
        Objects.requireNonNull(provider, "provider");
        Objects.requireNonNull(context, "context");

        return this.selector.evaluateValueText(
            CurrencyExchangeRaterPluginHelper.INSTANCE::parseName,
            provider::currencyExchangeRater,
            context
        );
    }

    // Object...........................................................................................................

    @Override
    public int hashCode() {
        return this.selector.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return this == other ||
            other instanceof CurrencyExchangeRaterSelector && this.equals0((CurrencyExchangeRaterSelector) other);
    }

    private boolean equals0(final CurrencyExchangeRaterSelector other) {
        return this.selector.equals(other.selector);
    }

    /**
     * Note it is intentional that the {@link #text()} is not quoted, to ensure {@link #parse(String)} and {@link #toString()}
     * are round-trippable.
     */
    @Override
    public String toString() {
        return this.selector.toString();
    }

    // HasContentType...................................................................................................

    public final static MediaType CONTENT_TYPE = HasContentType.json(CurrencyExchangeRaterSelector.class);

    @Override
    public Optional<MediaType> contentType() {
        return Optional.of(CONTENT_TYPE);
    }

    // JsonNodeContext..................................................................................................

    /**
     * Factory that creates a {@link CurrencyExchangeRaterSelector} from a {@link JsonNode}.
     */
    static CurrencyExchangeRaterSelector unmarshall(final JsonNode node,
                                                    final JsonNodeUnmarshallContext context) {
        return parse(node.stringOrFail());
    }

    private JsonNode marshall(final JsonNodeMarshallContext context) {
        return this.selector.marshall(context);
    }

    static {
        JsonNodeContext.register(
            JsonNodeContext.computeTypeName(CurrencyExchangeRaterSelector.class),
            CurrencyExchangeRaterSelector::unmarshall,
            CurrencyExchangeRaterSelector::marshall,
            CurrencyExchangeRaterSelector.class
        );
    }

    // TreePrintable....................................................................................................

    @Override
    public void printTree(final IndentingPrinter printer) {
        this.selector.printTree(printer);
    }
}
