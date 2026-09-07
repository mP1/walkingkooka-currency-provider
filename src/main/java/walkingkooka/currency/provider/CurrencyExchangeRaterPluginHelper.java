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

import walkingkooka.collect.set.Sets;
import walkingkooka.naming.Name;
import walkingkooka.net.AbsoluteUrl;
import walkingkooka.plugin.PluginAlias;
import walkingkooka.plugin.PluginHelper;
import walkingkooka.text.cursor.TextCursor;
import walkingkooka.text.cursor.parser.ParserContext;
import walkingkooka.text.cursor.parser.ParserToken;
import walkingkooka.text.cursor.parser.Parsers;
import walkingkooka.text.cursor.parser.StringParserToken;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.function.Function;

final class CurrencyExchangeRaterPluginHelper implements PluginHelper<CurrencyExchangeRaterName,
    CurrencyExchangeRaterInfo,
    CurrencyExchangeRaterInfoSet,
    CurrencyExchangeRaterSelector,
    CurrencyExchangeRaterAlias,
    CurrencyExchangeRaterAliasSet> {

    final static CurrencyExchangeRaterPluginHelper INSTANCE = new CurrencyExchangeRaterPluginHelper();

    private CurrencyExchangeRaterPluginHelper() {
    }

    @Override
    public CurrencyExchangeRaterName name(final String text) {
        return CurrencyExchangeRaterName.with(text);
    }

    @Override
    public Optional<CurrencyExchangeRaterName> parseName(final TextCursor cursor,
                                                         final ParserContext context) {
        Objects.requireNonNull(cursor, "cursor");
        Objects.requireNonNull(context, "context");

        return Parsers.initialAndPartCharPredicateString(
            c -> CurrencyExchangeRaterName.isChar(0, c),
            c -> CurrencyExchangeRaterName.isChar(1, c),
            CurrencyExchangeRaterName.MIN_LENGTH, // minLength
            CurrencyExchangeRaterName.MAX_LENGTH // maxLength
        ).parse(
            cursor,
            context
        ).map(
            (final ParserToken token) -> this.name(
                token.cast(StringParserToken.class).value()
            )
        );
    }

    @Override
    public Set<CurrencyExchangeRaterName> names(final Set<CurrencyExchangeRaterName> names) {
        return Sets.immutable(
            Objects.requireNonNull(names, "names")
        );
    }

    @Override
    public Function<CurrencyExchangeRaterName, RuntimeException> unknownName() {
        return n -> new IllegalArgumentException("Unknown CurrencyExchangeRater " + n);
    }

    @Override
    public Comparator<CurrencyExchangeRaterName> nameComparator() {
        return Name.comparator(CurrencyExchangeRaterName.CASE_SENSITIVITY);
    }

    @Override
    public CurrencyExchangeRaterInfo info(final AbsoluteUrl url,
                                          final CurrencyExchangeRaterName name) {
        return CurrencyExchangeRaterInfo.with(url, name);
    }

    @Override
    public CurrencyExchangeRaterInfo parseInfo(final String text) {
        return CurrencyExchangeRaterInfo.parse(text);
    }

    @Override
    public CurrencyExchangeRaterInfoSet infoSet(final Set<CurrencyExchangeRaterInfo> infos) {
        return CurrencyExchangeRaterInfoSet.with(infos);
    }

    @Override
    public CurrencyExchangeRaterSelector parseSelector(final String text) {
        return CurrencyExchangeRaterSelector.parse(text);
    }

    @Override
    public CurrencyExchangeRaterAlias alias(final CurrencyExchangeRaterName name,
                                            final Optional<CurrencyExchangeRaterSelector> selector,
                                            final Optional<AbsoluteUrl> url) {
        return CurrencyExchangeRaterAlias.with(
            name,
            selector,
            url
        );
    }

    @Override
    public CurrencyExchangeRaterAlias alias(final PluginAlias<CurrencyExchangeRaterName, CurrencyExchangeRaterSelector> pluginAlias) {
        return CurrencyExchangeRaterAlias.with(pluginAlias);
    }

    @Override
    public CurrencyExchangeRaterAliasSet aliasSet(final SortedSet<CurrencyExchangeRaterAlias> aliases) {
        return CurrencyExchangeRaterAliasSet.EMPTY.setElements(aliases);
    }

    @Override
    public String label() {
        return "CurrencyExchangeRater";
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
