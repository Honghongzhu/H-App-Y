import pandas as pd
import numpy as np
from operator import itemgetter
import json

def json_to_df(movie_json, user_json):

    movie_ratings_df = pd.DataFrame.from_dict(json.loads(movie_json))
    first_cols = ['averageMeaning', 'votesMeaning', 'averageEnjoyment', 'votesEnjoyment', 'movieId']
    cols = list(movie_ratings_df.columns)
    for col in first_cols:
        cols.insert(0, cols.pop(cols.index(col)))

    movie_ratings_df = movie_ratings_df[cols]
    # change the type from string
    movie_ratings_df = movie_ratings_df.astype({'averageMeaning': 'float64', 'votesMeaning': 'int64', 'averageEnjoyment': 'float64', 'votesEnjoyment': 'int64'})

    for i in range(5, len(movie_ratings_df.columns)):
        movie_ratings_df[movie_ratings_df.columns.tolist()[i]] = movie_ratings_df[movie_ratings_df.columns.tolist()[i]].astype('int64')

    user_ratings_df = pd.DataFrame.from_dict(json.loads(user_json))
    first_cols = ['meaningRating', 'enjoymentRating', 'movieId', 'userId', 'ratingId']
    cols = list(user_ratings_df.columns)
    for col in first_cols:
        cols.insert(0, cols.pop(cols.index(col)))

    user_ratings_df = user_ratings_df[cols]

    # change the type from string
    user_ratings_df = user_ratings_df.astype({'meaningRating': 'float64', 'enjoymentRating': 'float64'})

    for i in range(5, len(user_ratings_df.columns)):
        user_ratings_df[user_ratings_df.columns.tolist()[i]] = user_ratings_df[user_ratings_df.columns.tolist()[i]].astype('int64')

    return movie_ratings_df, user_ratings_df

def cosine_similarity(a, b):
    return np.dot(a,b)/(np.linalg.norm(a)*np.linalg.norm(b))

def get_user_score(user_ratings_df):
    '''
    Calculates the user profile's score based on character strengths and
    meaningful score. Only movies with meaningful score > 3.0 are taken
    into account.
    '''
    MEANING_RATING_COL = user_ratings_df.columns.tolist().index('meaningRating')

    relevant_df = user_ratings_df[user_ratings_df['meaningRating'] >= 3.0]
    meaning_scores = relevant_df.iloc[:, MEANING_RATING_COL].values

    # We want every value of meaning_scores to multiply a whole row of character strengths.
    # This is done by using a concept called "broadcasting"
    ch_strs = relevant_df.iloc[:, np.arange(MEANING_RATING_COL+1, len(relevant_df.columns))] * meaning_scores[:, np.newaxis]

    # we sum every character strength weighted score and we normalize it
    score = ch_strs.sum()/ch_strs.sum().sum()

    return score

def get_movie_score(movie_df, movie_id, max_meaningful_votes, general_score):
    '''
    Calculate a single movie's score using its character strengths and
    meaningful score. As the final score is calculated using a bayesian
    estimate, weight and a-priori score parameters are used.
    '''

    FIRST_CH_STR_COL = movie_df.columns.tolist().index('appreciationBeautyExcellence')

    relevant_df = movie_df[movie_df['movieId'] == movie_id]

    meaningful_scores = relevant_df['averageMeaning'].values[0]
    weight = relevant_df['votesMeaning'].values[0]/max_meaningful_votes

    ch_strs = relevant_df.iloc[:, np.arange(FIRST_CH_STR_COL, len(relevant_df.columns))] * meaningful_scores
    movie_score = ch_strs.sum() / ch_strs.sum().sum()

    return weight*movie_score + (1-weight)*general_score

def get_user_recommendations(movie_json, user_json):
    '''
    Returns a user's ranked recommendations by calculating the cosine
    similarity of its profile with every movie in the db.
    '''

    # first get the dataframes
    movie_ratings_df, user_ratings_df = json_to_df(movie_json, user_json)

    FIRST_CH_STR_COL = movie_ratings_df.columns.tolist().index('appreciationBeautyExcellence')

    user_score = get_user_score(user_ratings_df)

    # df with only the character strengths
    all_ch_strs = movie_ratings_df.iloc[:, np.arange(FIRST_CH_STR_COL, len(movie_ratings_df.columns))] * np.array(movie_ratings_df['averageMeaning'])[:, np.newaxis]
    # normalize very row (every movie)
    all_ch_strs = all_ch_strs.div(all_ch_strs.sum(axis=1), axis=0)

    # calculate the general score by averaging the scores of every movie
    general_score = all_ch_strs.mean(axis=0)

    max_meaningful_votes = movie_ratings_df['votesMeaning'].max()

    movie_cosine_sim = {}

    # calculate the cosine similarity between scores of every movie and the user's.
    for movie in movie_ratings_df['movieId']:
        movie_cosine_sim[movie] = cosine_similarity(user_score, get_movie_score(movie_ratings_df, movie, max_meaningful_votes, general_score))

    # sort the movies by similarity score (higher means better)
    sorted_similarities = {k: v for k, v in sorted(movie_cosine_sim.items(), key=itemgetter(1), reverse=True)}

    # this prints the user score together with the score of the top ranked movie
    #print(np.array(user_score), np.array(get_movie_score(ch_movie_df, list(sorted_similarities.keys())[0], max_meaningful_votes, general_score)))

    return list(sorted_similarities.keys())[:12], list(sorted_similarities.values())[:12]