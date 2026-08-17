<%@ page language="java" contentType="text/html;charset=UTF-8" session="true"%>
<script type="text/javascript">
    /*
     * Unlike the producer help, which is decorator driven and therefore loaded via ajax, the columns of the top
     * producers table are known at compile time. The whole help text is static markup, the only dynamic part is which
     * of the ranking category boxes is shown.
     */
    function showTopProducersHelpModal(rankingCategory){
        var $dialog = $($('#topproducers_help_template')[0]);
        if(!$dialog) return;
        $dialog.find('.modal-title').html('Top Producers: ' + rankingCategory);
        $dialog.find('.topproducers-help-category').hide();
        $dialog.find('.topproducers-help-category[data-category="' + rankingCategory + '"]').show();
        $dialog.modal('show');
    }
</script>
<div class="modal fade" id="topproducers_help_template" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog gray">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">Top Producers</h4>
            </div>
            <div class="modal-body">

                <%-- One box per net.anotheria.moskito.core.topproducers.Category value, the matching one is shown. --%>
                <div class="box topproducers-help-category" style="display: none;" data-category="REQUESTS">
                    <div class="box-title"><h3 class="pull-left">Ranking category: REQUESTS</h3></div>
                    <div class="box-content"><div class="paddner">
                        Ranks the producers by the number of requests they served during an interval.
                    </div></div>
                </div>
                <div class="box topproducers-help-category" style="display: none;" data-category="TOTAL_TIME">
                    <div class="box-title"><h3 class="pull-left">Ranking category: TOTAL_TIME</h3></div>
                    <div class="box-content"><div class="paddner">
                        Ranks the producers by the total time spent inside them during an interval. Since the time of all
                        concurrent requests is added up, this can exceed the length of the interval itself.
                    </div></div>
                </div>
                <div class="box topproducers-help-category" style="display: none;" data-category="ERRORS">
                    <div class="box-title"><h3 class="pull-left">Ranking category: ERRORS</h3></div>
                    <div class="box-content"><div class="paddner">
                        Ranks the producers by the number of errors they produced during an interval.
                    </div></div>
                </div>
                <div class="box topproducers-help-category" style="display: none;" data-category="MAX_CONCURRENT_REQUEST">
                    <div class="box-title"><h3 class="pull-left">Ranking category: MAX_CONCURRENT_REQUEST</h3></div>
                    <div class="box-content"><div class="paddner">
                        Ranks the producers by the highest number of requests they were serving at the same time during
                        an interval. Note that the peaks of different producers don't have to happen at the same moment,
                        so their sum is an upper bound of the peak of the system rather than an exact total - the share
                        column is an approximation in this category.
                    </div></div>
                </div>
                <div class="box topproducers-help-category" style="display: none;" data-category="ERROR_RATE">
                    <div class="box-title"><h3 class="pull-left">Ranking category: ERROR_RATE</h3></div>
                    <div class="box-content"><div class="paddner">
                        Ranks the producers by the share of their requests that failed during an interval. An error rate
                        is not additive, so unlike in the other categories the share column here is simply the error rate
                        itself: a producer failing every single request shows 100%, no matter how few requests it had.
                        That is on purpose - it is what makes a rarely used but broken producer visible.
                    </div></div>
                </div>

                <div class="box">
                    <div class="box-title"><h3 class="pull-left">How the ranking works</h3></div>
                    <div class="box-content"><div class="paddner">
                        Every time the configured interval is updated (1m by default), all producers are ranked against
                        each other for that interval, and each of them collects two scores: its <b>position</b> in the
                        ranking and its <b>share</b> of everything that happened in the category. Both are added up over
                        time, so the producers that consistently consume the most resources end up on top and can be used
                        as optimization targets. A producer that did nothing in a category during an interval is not
                        ranked in it and collects no score for it.
                    </div></div>
                </div>
                <div class="box">
                    <div class="box-title"><h3 class="pull-left">Total Score</h3></div>
                    <div class="box-content"><div class="paddner">
                        The sum of all position scores the producer collected. In a single interval a producer earns a
                        point for every producer ranked below it, so leading every interval accumulates the highest
                        total. A position score is robust against outliers, because it only uses the order of the
                        producers and not the distance between them - but for the same reason it cannot express
                        magnitude: a producer with a million requests scores only one point more than the one with ten
                        thousand. Use the share column when magnitude is what you are after.
                    </div></div>
                </div>
                <div class="box">
                    <div class="box-title"><h3 class="pull-left">Average Score</h3></div>
                    <div class="box-content"><div class="paddner">
                        The total score divided by the number of intervals the producer was ranked in. Unlike the total
                        score it does not grow with age, so a producer that showed up an hour ago can be compared with
                        one that has been running since startup.
                    </div></div>
                </div>
                <div class="box">
                    <div class="box-title"><h3 class="pull-left">Avg Share %</h3></div>
                    <div class="box-content"><div class="paddner">
                        The average share of the category the producer accounted for, per interval. 50 means that half of
                        all requests (or time, or errors) of all ranked producers belonged to this producer. This is the
                        number that bounds what optimizing it can buy you. It is normalized by the sum of all producers,
                        not by the largest one, so every interval distributes the same 100% - which is what makes the
                        intervals comparable and the score accumulatable. The flip side is that everything outside the
                        few heaviest producers is flattened towards zero; that is what the position based scores are for.
                    </div></div>
                </div>
                <div class="box">
                    <div class="box-title"><h3 class="pull-left">Top Score</h3></div>
                    <div class="box-content"><div class="paddner">
                        The highest position score the producer reached in a single interval, in other words its best
                        placement so far.
                    </div></div>
                </div>
                <div class="box">
                    <div class="box-title"><h3 class="pull-left">Last Score</h3></div>
                    <div class="box-content"><div class="paddner">
                        The position score of the most recently ranked interval. Compared with the average score it shows
                        whether a producer is currently heavier or lighter than it usually is.
                    </div></div>
                </div>
                <div class="box last">
                    <div class="box-title"><h3 class="pull-left">Intervals</h3></div>
                    <div class="box-content"><div class="paddner">
                        The number of intervals the producer has been ranked in this category. All the other columns are
                        based on exactly these intervals, so a producer with very few of them is ranked on very little
                        evidence.
                    </div></div>
                </div>

            </div>
        </div>
    </div>
</div>
